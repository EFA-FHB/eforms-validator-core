package com.nortal.efafhb.eforms.validator.validation.service;

import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.EFORMS_SDK_VERSION_DELIMITER;
import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.EXCLUDED_SCHEMATRON_RULES_DE;
import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.RESOURCE_PATH;

import com.helger.schematron.ISchematronResource;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.pure.model.PSPhase;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import com.helger.xml.transform.StringStreamSource;
import com.nortal.efafhb.eforms.validator.config.IgnoredRulesConfig;
import com.nortal.efafhb.eforms.validator.enums.EFormSupportedVersion;
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
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.StringUtils;

@JBossLog
@ApplicationScoped
@Startup
@IfBuildProperty(name = "eforms-validator.engine", stringValue = "phax")
@RequiredArgsConstructor
class PhaxNativeValidator implements FormsValidator {

  private static final String SCHEMATRON_LOCATION = RESOURCE_PATH + "native-validation/%s/%s";
  private static final String DEFAULT_DE_PHASE = "eforms-de-phase";
  private static final EnumMap<SupportedVersion, ISchematronResource> validatorsNativeEu =
      new EnumMap<>(SupportedVersion.class);
  private static final EnumMap<SupportedVersion, ISchematronResource> validatorsNativeDe =
      new EnumMap<>(SupportedVersion.class);
  private static final Map<Map<SupportedVersion, String>, ISchematronResource>
      validatorsByVersionAndPhase = new ConcurrentHashMap<>();

  private final ValidatorUtil validatorUtil;
  private final ValidationConfig validationConfig;
  private final IgnoredRulesConfig ignoredRulesConfig;

  @PostConstruct
  void init() {
    loadNative();
  }

  @Override
  public ValidationResult validate(
      SupportedType supportedType,
      String eforms,
      SupportedVersion eformsVersion,
      String requestedEformsVersion) {

    List<SchematronOutputType> schematronOutputs = new ArrayList<>();

    try {
      if (SupportedType.DE.equals(supportedType)) {
        // get EU version for provided DE version
        SupportedVersion euVersion = SupportedVersion.sdkVersionsForGdk().get(eformsVersion);
        var de =
            validatorsNativeDe
                .get(eformsVersion)
                .applySchematronValidationToSVRL(new StringStreamSource(eforms));
        var eu = validateEuNotice(eforms, euVersion);
        schematronOutputs.add(de);
        schematronOutputs.add(eu);
      } else if (validationIsPossibleByPhase(requestedEformsVersion)) {
        schematronOutputs.add(validateByPhase(eforms, eformsVersion));
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

  private SchematronOutputType validateEuNotice(String eforms, SupportedVersion euVersion)
      throws Exception {
    String eFormsVersion = EFormSupportedVersion.getEFormsVersion(euVersion.getValue());
    if (validationIsPossibleByPhase(eFormsVersion)) {
      try {
        return validateByPhase(eforms, euVersion);
      } catch (Exception e) {
        log.debugf(
            "No resource found for euVersion %s and subtype %s. Using default phase.",
            euVersion.getValue(), validatorUtil.extractNoticeSubType(eforms));
      }
    }
    return validateByDefaultPhase(eforms, euVersion);
  }

  private SchematronOutputType validateByPhase(String eforms, SupportedVersion euVersion)
      throws Exception {
    try {
      return validatorsByVersionAndPhase
          .get(Map.of(euVersion, validatorUtil.extractNoticeSubType(eforms)))
          .applySchematronValidationToSVRL(new StringStreamSource(eforms));
    } catch (Exception e) {
      log.debugf(
          "No resource found for euVersion %s and subtype %s. Using default phase.",
          euVersion.getValue(), validatorUtil.extractNoticeSubType(eforms));
    }
    return validateByDefaultPhase(eforms, euVersion);
  }

  private SchematronOutputType validateByDefaultPhase(String eforms, SupportedVersion version)
      throws Exception {
    return validatorsNativeEu
        .get(version)
        .applySchematronValidationToSVRL(new StringStreamSource(eforms));
  }

  private boolean validationIsPossibleByPhase(String eformsVersion) {
    return validationConfig.eformVersionsValidatedByPhase().contains(eformsVersion);
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

  private boolean isEuRuleExcluded(String ruleId, SupportedType supportedType) {
    return SupportedType.DE.equals(supportedType) && EXCLUDED_SCHEMATRON_RULES_DE.contains(ruleId);
  }

  private boolean isRuleIgnored(String ruleId, String sdkVersion) {
    boolean isIgnored = ignoredRulesConfig.getIgnoredRules(sdkVersion).contains(ruleId);
    if (isIgnored) {
      log.infof(
          "Rule with id %s was executed and failed, but the error/warning is not included "
              + "in the result, as it was configured to be skipped by configuration file %s",
          ruleId, ignoredRulesConfig.getConfigFileName());
    }
    return isIgnored;
  }

  private void addFailedAssertsToValidationResult(
      SupportedVersion eformsVersion,
      SupportedType supportedType,
      List<SchematronOutputType> schematronOutputs,
      ValidationResult validationResult) {

    String sdkVersion =
        StringUtils.joinWith(
            EFORMS_SDK_VERSION_DELIMITER,
            supportedType.getStandardizedName(),
            eformsVersion.getValue());

    schematronOutputs.forEach(
        so ->
            SchematronHelper.getAllFailedAssertions(so).stream()
                .filter(
                    schematronFailedAssert ->
                        !isEuRuleExcluded(schematronFailedAssert.getID(), supportedType)
                            && !isRuleIgnored(schematronFailedAssert.getID(), sdkVersion))
                .forEach(
                    schematronFailedAssert ->
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
                                schematronFailedAssert.getLocation()))));
  }

  void loadNative() {
    log.info("loading phax native schematron validator...");
    validationConfig.supportedEFormsVersions().forEach(this::loadSchematron);
    log.info("loading phax native schematron validator completed!");
  }

  private void loadSchematron(String version) {
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

    SchematronResourcePure aResPure =
        SchematronResourcePure.fromClassPath(path, this.getClass().getClassLoader());

    if (SupportedType.DE.equals(supportedType)) {
      applyPhase(aResPure, path);
    }

    if (validationIsPossibleByPhase(version)) {
      CompletableFuture.runAsync(
          () -> {
            applyPhaseForEachSubtype(path, supportedVersion);
            log.debugf("Resource added for all phases in path '%s'", path);
          });
    }

    if (!aResPure.isValidSchematron()) {
      throw new IllegalArgumentException("Invalid Schematron!");
    }

    getValidators(supportedType).put(supportedVersion, aResPure);
  }

  private void applyPhaseForEachSubtype(String path, SupportedVersion supportedVersion) {
    final ExecutorService executorService = Executors.newCachedThreadPool();
    SchematronResourcePure.fromClassPath(path, this.getClass().getClassLoader())
        .getOrCreateBoundSchema()
        .getOriginalSchema()
        .getAllPhaseIDs()
        .forEach(
            phaseId ->
                CompletableFuture.runAsync(
                    () -> addResource(path, supportedVersion, phaseId), executorService));
    executorService.shutdown();
  }

  private void addResource(String path, SupportedVersion supportedVersion, String phaseId) {
    log.debugf("Adding resource for phase '%s' in path '%s'", phaseId, path);
    SchematronResourcePure resource =
        SchematronResourcePure.fromClassPath(path, this.getClass().getClassLoader())
            .setPhase(phaseId);
    resource.getOrCreateBoundSchema();
    validatorsByVersionAndPhase.put(Map.of(supportedVersion, phaseId), resource);
    log.debugf("Resource added for phase '%s' in path '%s': ", phaseId, path);
  }

  private void applyPhase(SchematronResourcePure aResPure, String schematronPath) {
    boolean phaseExists =
        SchematronResourcePure.fromClassPath(schematronPath, this.getClass().getClassLoader())
            .getOrCreateBoundSchema()
            .getOriginalSchema()
            .getAllPhases()
            .stream()
            .map(PSPhase::getID)
            .anyMatch(id -> validationConfig.deSchematronPhase().equals(id));

    if (phaseExists) {
      aResPure.setPhase(validationConfig.deSchematronPhase());
    } else {
      log.infof(
          "Phase '%s' not found in schematron %s, using default phase '%s'",
          validationConfig.deSchematronPhase(), schematronPath, DEFAULT_DE_PHASE);
      aResPure.setPhase(DEFAULT_DE_PHASE);
    }
  }

  private String getSchematronEntryFileName(SupportedVersion version) {
    switch (version) {
      case V0_1_1:
        return "entry.sch";
      case V1_0_0:
      case V1_5_5:
      case V1_7_3:
      case V1_10_1:
        return "complete-validation.sch";
      case V1_0_1:
      case V1_1_0:
      case V1_2_0:
        return "eforms-de-validation.sch";
      default:
        throw new IllegalArgumentException("Unsupported version: " + version);
    }
  }

  private EnumMap<SupportedVersion, ISchematronResource> getValidators(SupportedType type) {
    switch (type) {
      case DE:
        return validatorsNativeDe;
      case EU:
        return validatorsNativeEu;
      default:
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
  }
}
