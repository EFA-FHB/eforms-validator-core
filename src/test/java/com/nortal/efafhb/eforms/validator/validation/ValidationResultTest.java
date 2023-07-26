package com.nortal.efafhb.eforms.validator.validation;

import com.nortal.efafhb.eforms.validator.enums.InfoLevel;
import com.nortal.efafhb.eforms.validator.enums.ReportType;
import com.nortal.efafhb.eforms.validator.validation.entry.ValidationEntry;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidationResultTest {

  private ValidationResult validationResult;

  @BeforeEach
  void setUp() {
    validationResult = new ValidationResult();
  }

  @Test
  void testAddValidationToReport_AddingEntries_CheckIfCorrectlyStored() {
    ValidationEntry entry1 = ValidationEntry.builder()
        .formattedMessage("Error 1")
        .path("/path/to/error")
        .rule("Rule 1")
        .test("Test 1")
        .type("Type 1")
        .build();

    ValidationEntry entry2 = ValidationEntry.builder()
        .formattedMessage("Warning 1")
        .path("/path/to/warning")
        .rule("Rule 2")
        .test("Test 2")
        .type("Type 2")
        .build();

    ValidationEntry entry3 = ValidationEntry.builder()
        .formattedMessage("Error 2")
        .path("/path/to/error")
        .rule("Rule 3")
        .test("Test 3")
        .type("Type 3")
        .build();

    validationResult.addValidationToReport(ReportType.SCHEMATRON, InfoLevel.ERROR, entry1);
    validationResult.addValidationToReport(ReportType.SCHEMATRON, InfoLevel.WARNING, entry2);
    validationResult.addValidationToReport(ReportType.XSD, InfoLevel.ERROR, entry3);

    Set<ValidationEntry> errors = validationResult.getErrors();
    Set<ValidationEntry> warnings = validationResult.getWarnings();

    assertEquals(2, errors.size());
    assertTrue(errors.contains(entry1));
    assertTrue(errors.contains(entry3));

    assertEquals(1, warnings.size());
    assertTrue(warnings.contains(entry2));
  }

  @Test
  void testGetErrors_NoErrors_ReturnsEmptySet() {
    ValidationEntry entry1 = ValidationEntry.builder()
        .formattedMessage("Warning 1")
        .path("/path/to/warning")
        .rule("Rule 1")
        .test("Test 1")
        .type("Type 1")
        .build();

    ValidationEntry entry2 = ValidationEntry.builder()
        .formattedMessage("Warning 2")
        .path("/path/to/warning")
        .rule("Rule 2")
        .test("Test 2")
        .type("Type 2")
        .build();

    validationResult.addValidationToReport(ReportType.SCHEMATRON, InfoLevel.WARNING, entry1);
    validationResult.addValidationToReport(ReportType.XSD, InfoLevel.WARNING, entry2);

    assertTrue(validationResult.getErrors().isEmpty());
  }

  @Test
  void testGetWarnings_NoWarnings_ReturnsEmptySet() {
    ValidationEntry entry1 = ValidationEntry.builder()
        .formattedMessage("Error 1")
        .path("/path/to/error")
        .rule("Rule 1")
        .test("Test 1")
        .type("Type 1")
        .build();

    ValidationEntry entry2 = ValidationEntry.builder()
        .formattedMessage("Error 2")
        .path("/path/to/error")
        .rule("Rule 2")
        .test("Test 2")
        .type("Type 2")
        .build();

    validationResult.addValidationToReport(ReportType.SCHEMATRON, InfoLevel.ERROR, entry1);
    validationResult.addValidationToReport(ReportType.XSD, InfoLevel.ERROR, entry2);

    assertTrue(validationResult.getWarnings().isEmpty());
  }

  @Test
  void testAddValidationToReport_WithDescriptionAndType_DescriptionAndTypeCorrectlyStored() {
    ValidationEntry entry1 = ValidationEntry.builder()
        .formattedMessage("Error 1")
        .path("/path/to/error")
        .rule("Rule 1")
        .test("Test 1")
        .type("Type 1")
        .description("Description 1")
        .build();

    validationResult.addValidationToReport(ReportType.SCHEMATRON, InfoLevel.ERROR, entry1);

    Set<ValidationEntry> errors = validationResult.getErrors();
    assertEquals(1, errors.size());
    ValidationEntry storedEntry = errors.iterator().next();
    assertEquals("Description 1", storedEntry.getDescription());
    assertEquals("Type 1", storedEntry.getType());
  }
}
