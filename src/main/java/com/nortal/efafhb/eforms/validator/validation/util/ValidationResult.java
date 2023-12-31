package com.nortal.efafhb.eforms.validator.validation.util;

import com.nortal.efafhb.eforms.validator.enums.InfoLevel;
import com.nortal.efafhb.eforms.validator.enums.ReportType;
import com.nortal.efafhb.eforms.validator.validation.entry.ValidationEntry;
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
   * Adds a validation entry to the validation report.
   *
   * @param reportType the source of the validation
   * @param infoLevel the level of the validation
   * @param validationEntry the validation details
   */
  public void addValidationToReport(
      ReportType reportType, InfoLevel infoLevel, ValidationEntry validationEntry) {
    report.get(reportType).get(infoLevel).add(validationEntry);
  }

  /**
   * Returns all errors and unknown statuses found during validations.
   *
   * @return a set of errors
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
   * Returns all warnings found during validation.
   *
   * @return a set of warnings
   */
  public Set<ValidationEntry> getWarnings() {
    Set<ValidationEntry> warnings = new HashSet<>();
    report.forEach((key, value) -> warnings.addAll(value.get(InfoLevel.WARNING)));
    return Collections.unmodifiableSet(warnings);
  }
}
