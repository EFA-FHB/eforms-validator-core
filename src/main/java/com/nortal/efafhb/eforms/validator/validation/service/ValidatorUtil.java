package com.nortal.efafhb.eforms.validator.validation.service;

import com.nortal.efafhb.eforms.validator.enums.InfoLevel;
import com.nortal.efafhb.eforms.validator.enums.ReportType;
import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.validation.entry.ValidationEntry;
import com.nortal.efafhb.eforms.validator.validation.util.Translate;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.StringUtils;

@Singleton
@JBossLog
public class ValidatorUtil {

  @Inject Translate translate;

  public static final String RESOURCE_PATH = "schematron/%s/";
  public static final String EFORMS_SDK_VERSION_DELIMITER = "-";

  private static final String MESSAGE_PATTERN_SCHEMATRON = "Rule: %s ; Test: %s ; Location: %s";

  static final String VALIDATION_ENTRY_SCHEMATRON_TYPE = "SCHEMATRON";
  public static final String VALIDATION_ENTRY_XSD_TYPE = "XSD";
  public static final String XSD_VALIDATION_FAILED_CODE = "XSD_VALIDATION_FAILED_CODE";
  static final String VALIDATION_ENTRY_T015_SCHEMATRON_TYPE = "BUSINESS_DOCUMENT_VALIDATION_FAILED";
  private static final String EXCLUDED_SCHEMATRON_RULES_DE_RESOURCE_PATH =
      RESOURCE_PATH + "excluded_rules.txt";
  protected static final List<String> EXCLUDED_SCHEMATRON_RULES_DE =
      readExcludedSchematronRules(String.format(EXCLUDED_SCHEMATRON_RULES_DE_RESOURCE_PATH, "de"));
  static final Map<String, InfoLevel> INFO_LEVEL_MAPPINGS = initInfoLevelMappings();

  /**
   * Creates message from content, test and location of schematron assert
   *
   * @param version sdk version
   * @param content message content
   * @param test assert test
   * @param location location in xsd
   * @return validation details
   */
  public ValidationEntry createValidationEntry(
      SupportedVersion version,
      SupportedType supportedType,
      String content,
      String test,
      String location) {
    String formattedMessage = String.format(MESSAGE_PATTERN_SCHEMATRON, content, test, location);
    return ValidationEntry.builder()
        .formattedMessage(formattedMessage)
        .rule(content)
        .path(location)
        .test(test)
        .description(translate.translate(version, supportedType, content))
        .type(getType(ReportType.SCHEMATRON))
        .build();
  }

  /**
   * Maps string value of role (or flag if role is not present) to corresponding InfoLevel
   *
   * @param role level of error in string
   * @param flag additional information on level of error in string
   * @return level of error as enum
   */
  public InfoLevel getSchematronErrorLevel(String role, String flag) {
    String errorLevelIndicator = StringUtils.isBlank(role) ? flag : role;
    if (StringUtils.isBlank(errorLevelIndicator)) {
      log.warn("Unrecognized schematron assert log level: null");
      return InfoLevel.UNKNOWN;
    }
    InfoLevel infoLevel = INFO_LEVEL_MAPPINGS.get(errorLevelIndicator.toUpperCase());
    if (Objects.isNull(infoLevel)) {
      log.warn(
          String.format("Unrecognized schematron assert error level: %s", errorLevelIndicator));
      return InfoLevel.UNKNOWN;
    }
    return infoLevel;
  }

  public static String getType(ReportType reportType) {
    switch (reportType) {
      case SCHEMATRON:
        return VALIDATION_ENTRY_SCHEMATRON_TYPE;
      case XSD:
        return VALIDATION_ENTRY_XSD_TYPE;
      default:
        throw new IllegalArgumentException("Unsupported report type: " + reportType);
    }
  }

  public ValidationEntry createBusinessDocumentValidationEntry(
      String content, String test, String location) {
    String formattedMessage = String.format(MESSAGE_PATTERN_SCHEMATRON, content, test, location);
    return ValidationEntry.builder()
        .formattedMessage(formattedMessage)
        .rule(content)
        .path(location)
        .test(test)
        .type(VALIDATION_ENTRY_T015_SCHEMATRON_TYPE)
        .build();
  }

  private static List<String> readExcludedSchematronRules(String fileName) {
    List<String> rules = new ArrayList<>();
    try (InputStream inputStream =
        PhaxNativeValidator.class.getClassLoader().getResourceAsStream(fileName)) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      while (reader.ready()) {
        rules.add(reader.readLine());
      }
    } catch (IOException e) {
      log.error(String.format("Something went wrong while reading file '%s'.", fileName));
    }
    return Collections.unmodifiableList(rules);
  }

  private static List<String> getErrorLevelIndicators() {
    return List.of("FATAL", "FATAL_ERROR", "FATAL-ERROR", "FATALERROR", "ERROR", "ERR");
  }

  private static List<String> getWarningLevelIndicators() {
    return List.of("WARNING", "WARN");
  }

  private static List<String> getInfoLevelIndicators() {
    return List.of("INFORMATION", "INFO", "NOTICE", "NOTE");
  }

  private static Map<String, InfoLevel> initInfoLevelMappings() {
    Map<String, InfoLevel> infoLevelMappings = new HashMap<>();
    getErrorLevelIndicators().forEach(err -> infoLevelMappings.put(err, InfoLevel.ERROR));
    getWarningLevelIndicators().forEach(warn -> infoLevelMappings.put(warn, InfoLevel.WARNING));
    getInfoLevelIndicators().forEach(info -> infoLevelMappings.put(info, InfoLevel.INFO));
    return infoLevelMappings;
  }
}
