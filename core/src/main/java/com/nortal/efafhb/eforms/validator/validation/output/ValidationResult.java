package com.nortal.efafhb.eforms.validator.validation.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public class ValidationResult {

  @Setter @Getter private String sdkValidationVersion;

  @Setter @Getter private String xmlReport;

  private final EnumMap<ReportType, EnumMap<InfoLevel, List<ValidationEntry>>> report =
      new EnumMap<>(
          Map.of(
              ReportType.SCHEMATRON, emptyEnumMapInfoLevel(),
              ReportType.XSD, emptyEnumMapInfoLevel()));

  private static EnumMap<InfoLevel, List<ValidationEntry>> emptyEnumMapInfoLevel() {
    return new EnumMap<>(
        Map.of(
            InfoLevel.WARNING, new ArrayList<>(),
            InfoLevel.ERROR, new ArrayList<>(),
            InfoLevel.UNKNOWN, new ArrayList<>(),
            InfoLevel.INFO, new ArrayList<>()));
  }

  /**
   * Method used to add validation details
   *
   * @param reportType source of validation
   * @param infoLevel level of validation
   * @param validationEntry validation details
   */
  public void addValidationToReport(
      ReportType reportType, InfoLevel infoLevel, ValidationEntry validationEntry) {
    report.get(reportType).get(infoLevel).add(validationEntry);
  }

  /**
   * Returns all errors and unknown statuses found during validations
   *
   * @return errors
   */
  public Set<ValidationEntry> getErrors() {
    Set<ValidationEntry> errors = new HashSet<>();
    report.forEach(
        (key, value) -> {
          errors.addAll(value.get(InfoLevel.ERROR));
          errors.addAll(value.get(InfoLevel.UNKNOWN));
        });
    return Collections.unmodifiableSet(errors);
  }

  /**
   * Returns all warnings found during validation
   *
   * @return warnings
   */
  public Set<ValidationEntry> getWarnings() {
    Set<ValidationEntry> warnings = new HashSet<>();
    report.forEach((key, value) -> warnings.addAll(value.get(InfoLevel.WARNING)));
    return Collections.unmodifiableSet(warnings);
  }
}
