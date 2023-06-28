package com.nortal.efafhb.eforms.validator.validation.service;

import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.EXCLUDED_SCHEMATRON_RULES_DE;

import com.nortal.efafhb.eforms.validator.enums.ReportType;
import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.validation.FormsValidator;
import com.nortal.efafhb.eforms.validator.validation.ValidationConfig;
import com.nortal.efafhb.eforms.validator.validation.entry.ValidationEntry;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import de.kosit.validationtool.api.Check;
import de.kosit.validationtool.api.Configuration;
import de.kosit.validationtool.api.Input;
import de.kosit.validationtool.api.InputFactory;
import de.kosit.validationtool.api.Result;
import de.kosit.validationtool.impl.DefaultCheck;
import de.kosit.validationtool.impl.xml.ProcessorProvider;
import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.StringUtils;
import org.oclc.purl.dsdl.svrl.FailedAssert;

@JBossLog
@ApplicationScoped
@Startup
@IfBuildProperty(name = "eforms-validator.engine", stringValue = "kosit")
public class KositValidator implements FormsValidator {

  private static final String KOSIT_SCENARIOS_PATH =
      ValidatorUtil.RESOURCE_PATH + "kosit/%s/scenarios.xml";

  private static final EnumMap<SupportedVersion, Check> validatorsEu =
      new EnumMap<>(SupportedVersion.class);
  private static final EnumMap<SupportedVersion, Check> validatorsDe =
      new EnumMap<>(SupportedVersion.class);

  @Inject ValidatorUtil validatorUtil;

  @Inject ValidationConfig validationConfig;

  @PostConstruct
  void init() {
    loadKositConfigurations();
  }

  @Override
  public ValidationResult validate(
      SupportedType supportedType, String eforms, SupportedVersion eformsVersion) {

    if (!getValidators(supportedType).containsKey(eformsVersion)) {
      throw new IllegalArgumentException(
          String.format(
              "Not supported eforms %s with version %s",
              supportedType.getValue(), eformsVersion.getValue()));
    }

    ValidationResult result = new ValidationResult();

    List<Result> reports = getReports(supportedType, eforms, eformsVersion);

    validateSchematron(reports, supportedType, eformsVersion, result);

    result.setSdkValidationVersion(
        String.join(
            ValidatorUtil.EFORMS_SDK_VERSION_DELIMITER,
            supportedType.getStandardizedName(),
            eformsVersion.getValue()));
    return result;
  }

  private List<Result> getReports(
      SupportedType supportedType, String eforms, SupportedVersion eformsVersion) {
    Input document =
        InputFactory.read(eforms.getBytes(StandardCharsets.UTF_8), UUID.randomUUID().toString());

    List<Result> reports = new ArrayList<>();

    if (SupportedType.DE.equals(supportedType)) {
      SupportedVersion euVersion = SupportedVersion.sdkVersionsForGdk().get(eformsVersion);
      var reportDe = validatorsDe.get(eformsVersion).checkInput(document);
      var reportEu = validatorsEu.get(euVersion).checkInput(document);

      reports.add(reportDe);
      reports.add(reportEu);
    } else {
      reports.add(getValidators(supportedType).get(eformsVersion).checkInput(document));
    }
    return reports;
  }

  private void validateSchematron(
      List<Result> reports,
      SupportedType supportedType,
      SupportedVersion eformsVersion,
      ValidationResult result) {
    reports.forEach(
        report -> {
          if (!report.isSchematronValid()) {
            report
                .getFailedAsserts()
                .forEach(
                    failedAssert -> {
                      // content is in form rule|text|BR-BT-00514-0304
                      String content =
                          failedAssert.getText().getContent().stream()
                              .findFirst()
                              .orElse("")
                              .toString();
                      if (!SupportedType.DE.equals(supportedType)
                          || !EXCLUDED_SCHEMATRON_RULES_DE.contains(
                              StringUtils.replace(content, "rule|text|", StringUtils.EMPTY))) {
                        result.addValidationToReport(
                            ReportType.SCHEMATRON,
                            validatorUtil.getSchematronErrorLevel(
                                Objects.isNull(failedAssert.getRole())
                                    ? null
                                    : failedAssert.getRole().value(),
                                failedAssert.getFlag()),
                            createValidationDetails(eformsVersion, supportedType, failedAssert));
                      }
                    });
          }
          result.setXmlReport(report.getReport().toString());
        });
  }

  private ValidationEntry createValidationDetails(
      SupportedVersion eformsVersion, SupportedType supportedType, FailedAssert failedAssert) {
    return validatorUtil.createValidationEntry(
        eformsVersion,
        supportedType,
        failedAssert.getText().getContent().stream().findFirst().orElse("").toString(),
        failedAssert.getTest(),
        failedAssert.getLocation());
  }

  private void loadKositConfigurations() {
    log.info("loading Kosit validator configurations...");
    validationConfig
        .supportedEFormsVersions()
        .forEach(
            version -> {
              SupportedVersion supportedVersion = SupportedVersion.versionFromSDK(version);
              SupportedType supportedType = SupportedType.typeFromSDK(version);
              String supportedTypeName = supportedType.name().toLowerCase();
              String resourcePath =
                  String.format(
                      KOSIT_SCENARIOS_PATH, supportedTypeName, supportedVersion.getValue());
              URL scenarios = this.getClass().getClassLoader().getResource(resourcePath);
              if (Objects.isNull(scenarios)) {
                throw new IllegalStateException(
                    String.format("Can not load kosit resource: %s", resourcePath));
              }
              log.info(String.format("Loading Kosit validator configuration: %s ", resourcePath));
              Configuration config = loadConfiguration(scenarios);
              getValidators(supportedType).put(supportedVersion, new DefaultCheck(config));
            });
    log.info("loading Kosit validator configurations completed!");
  }

  private Configuration loadConfiguration(URL scenarios) {
    try {
      return Configuration.load(scenarios.toURI()).build(ProcessorProvider.getProcessor());
    } catch (URISyntaxException e) {
      log.error("Error loading kosit configuration", e);
      throw new IllegalStateException(e);
    }
  }

  private EnumMap<SupportedVersion, Check> getValidators(SupportedType supportedType) {
    return switch (supportedType) {
      case DE -> validatorsDe;
      case EU -> validatorsEu;
    };
  }
}
