package com.nortal.efafhb.eforms.validator.validation.service;

import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.EFORMS_SDK_VERSION_DELIMITER;
import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.EXCLUDED_SCHEMATRON_RULES_DE;
import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.RESOURCE_PATH;

import com.helger.schematron.ISchematronResource;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import com.helger.xml.transform.StringStreamSource;
import com.nortal.efafhb.eforms.validator.enums.ReportType;
import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.exception.ErrorCode;
import com.nortal.efafhb.eforms.validator.exception.ValidatorApplicationException;
import com.nortal.efafhb.eforms.validator.validation.FormsValidator;
import com.nortal.efafhb.eforms.validator.validation.ValidationConfig;
import com.nortal.efafhb.eforms.validator.validation.schematron.SchematronHelper;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
@Startup
@IfBuildProperty(name = "eforms-validator.engine", stringValue = "phax")
class PhaxNativeValidator implements FormsValidator {

  private static final String SCHEMATRON_LOCATION = RESOURCE_PATH + "native-validation/%s/%s";
  private static final EnumMap<SupportedVersion, ISchematronResource> validatorsNativeEu =
      new EnumMap<>(SupportedVersion.class);
  private static final EnumMap<SupportedVersion, ISchematronResource> validatorsNativeDe =
      new EnumMap<>(SupportedVersion.class);

  @Inject ValidatorUtil validatorUtil;
  @Inject ValidationConfig validationConfig;

  @PostConstruct
  void init() {
    loadNative();
  }

  @Override
  public ValidationResult validate(
      SupportedType supportedType, String eforms, SupportedVersion eformsVersion) {

    List<SchematronOutputType> schematronOutputs = new ArrayList<>();

    try {
      if (SupportedType.DE.equals(supportedType)) {
        // get EU version for provided DE version
        SupportedVersion euVersion = SupportedVersion.sdkVersionsForGdk().get(eformsVersion);
        var de =
            validatorsNativeDe
                .get(eformsVersion)
                .applySchematronValidationToSVRL(new StringStreamSource(eforms));
        var eu =
            validatorsNativeEu
                .get(euVersion)
                .applySchematronValidationToSVRL(new StringStreamSource(eforms));
        schematronOutputs.add(de);
        schematronOutputs.add(eu);
      } else {
        SchematronOutputType schematronOutput =
            getValidators(supportedType)
                .get(eformsVersion)
                .applySchematronValidationToSVRL(new StringStreamSource(eforms));
        schematronOutputs.add(schematronOutput);
      }
      return createValidationResult(schematronOutputs, eformsVersion, supportedType);
    } catch (Exception e) {
      log.warn("Exception occurred while reading source ", e);
      throw new ValidatorApplicationException(ErrorCode.MALFORMED_XML);
    }
  }

  private ValidationResult createValidationResult(
      List<SchematronOutputType> schematronOutputs,
      SupportedVersion eformsVersion,
      SupportedType supportedType) {
    ValidationResult validationResult = new ValidationResult();
    validationResult.setSdkValidationVersion(
        String.join(
            EFORMS_SDK_VERSION_DELIMITER,
            supportedType.getStandardizedName(),
            eformsVersion.getValue()));
    addFailedAssertsToValidationResult(
        eformsVersion, supportedType, schematronOutputs, validationResult);
    return validationResult;
  }

  private void addFailedAssertsToValidationResult(
      SupportedVersion eformsVersion,
      SupportedType supportedType,
      List<SchematronOutputType> schematronOutputs,
      ValidationResult validationResult) {

    schematronOutputs.forEach(
        so ->
            SchematronHelper.getAllFailedAssertions(so)
                .forEach(
                    schematronFailedAssert -> {
                      if (!SupportedType.DE.equals(supportedType)
                          || !EXCLUDED_SCHEMATRON_RULES_DE.contains(
                              schematronFailedAssert.getID())) {
                        validationResult.addValidationToReport(
                            ReportType.SCHEMATRON,
                            validatorUtil.getSchematronErrorLevel(
                                schematronFailedAssert.getRole(),
                                schematronFailedAssert.getFlagValue()),
                            validatorUtil.createValidationEntry(
                                eformsVersion,
                                supportedType,
                                schematronFailedAssert.getText(),
                                schematronFailedAssert.getTest(),
                                schematronFailedAssert.getLocation()));
                      }
                    }));
  }

  void loadNative() {
    log.info("loading phax native schematron validator...");
    validationConfig
        .supportedEFormsVersions()
        .forEach(
            version -> {
              SupportedVersion supportedVersion = SupportedVersion.versionFromSDK(version);
              SupportedType supportedType = SupportedType.typeFromSDK(version);
              String supportedTypeName = supportedType.name().toLowerCase();
              String path =
                  String.format(
                      SCHEMATRON_LOCATION,
                      supportedTypeName,
                      supportedVersion.getValue(),
                      getSchematronEntryFileName(supportedVersion));

              log.debugf("loading schematron resource: %s", path);
              final ISchematronResource aResPure =
                  SchematronResourcePure.fromClassPath(path, this.getClass().getClassLoader());
              if (!aResPure.isValidSchematron()) {
                throw new IllegalArgumentException("Invalid Schematron!");
              }
              getValidators(supportedType).put(supportedVersion, aResPure);
            });
    log.info("loading phax native schematron validator completed!");
  }

  private String getSchematronEntryFileName(SupportedVersion version) {
    return switch (version) {
      case V0_1_1 -> "entry.sch";
      case V1_0_0, V1_5_1, V1_7_0 -> "complete-validation.sch";
      case V1_0_1, V1_1_0 -> "eforms-de-validation.sch";
    };
  }

  private EnumMap<SupportedVersion, ISchematronResource> getValidators(
      SupportedType supportedType) {
    return switch (supportedType) {
      case DE -> validatorsNativeDe;
      case EU -> validatorsNativeEu;
    };
  }
}
