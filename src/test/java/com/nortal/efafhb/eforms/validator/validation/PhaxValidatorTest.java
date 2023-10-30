package com.nortal.efafhb.eforms.validator.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.validation.profiles.PhaxValidatorProfile;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(PhaxValidatorProfile.class)
class PhaxValidatorTest {

  private static final String CN_24_MAXIMAL_XML_ERROR = "cn_24_maximal_error.xml";
  private static final String CN_24_MINIMAL_XML = "cn_24_minimal.xml";
  private static final String CN_24_MINIMAL_V_0_1_XML = "cn_24_minimal_V0.1.xml";
  private static final String NOTICE_CN_DE_11_WARNING_AND_ERROR =
      "notice_cn_de_11_warning_and_error.xml";
  private static final String NOTICE_CN_DE_10 = "notice_cn_de_10.xml";
  private static final String NOTICE_SDK_1_5 = "eform-sdk-1.5.xml";

  @Inject FormsValidator schematronValidator;

  @Test
  void validate() throws IOException {
    String eformsMaxWithError = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML_ERROR);
    String eformsMin = readFromEFormsResourceAsString(CN_24_MINIMAL_XML);
    String eformsV01 = readFromEFormsResourceAsString(CN_24_MINIMAL_V_0_1_XML);

    ValidationResult reportMaxWithError =
        schematronValidator.validate(SupportedType.EU, eformsMaxWithError, SupportedVersion.V1_0_0);
    assertEquals(0, reportMaxWithError.getWarnings().size());
    assertNotEquals(0, reportMaxWithError.getErrors().size());

    ValidationResult reportMin =
        schematronValidator.validate(SupportedType.EU, eformsMin, SupportedVersion.V1_0_0);
    assertNotEquals(0, reportMin.getWarnings().size());
    assertEquals(0, reportMin.getErrors().size());

    ValidationResult reportWrongVersion =
        schematronValidator.validate(SupportedType.EU, eformsV01, SupportedVersion.V1_0_0);
    assertEquals(0, reportWrongVersion.getWarnings().size());
    assertNotEquals(0, reportWrongVersion.getErrors().size());

    ValidationResult reportMinVersion01 =
        schematronValidator.validate(SupportedType.EU, eformsV01, SupportedVersion.V0_1_1);
    assertEquals(0, reportMinVersion01.getWarnings().size());
    assertEquals(0, reportMinVersion01.getErrors().size());
  }

  @Test
  void validateErrorDetails() throws IOException {
    String eformsWithError = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML_ERROR);
    String eformsWithWarning = readFromEFormsResourceAsString(CN_24_MINIMAL_XML);

    ValidationResult reportWithError =
        schematronValidator.validate(SupportedType.EU, eformsWithError, SupportedVersion.V1_0_0);
    assertFalse(reportWithError.getErrors().isEmpty());
    // no ignored rules
    assertEquals(2, reportWithError.getErrors().size());
    reportWithError
        .getErrors()
        .forEach(
            error -> {
              assertNotNull(error.getRule());
              assertNotNull(error.getPath());
              assertNotNull(error.getTest());
              assertNotNull(error.getType());
            });

    ValidationResult reportWithWarning =
        schematronValidator.validate(SupportedType.EU, eformsWithWarning, SupportedVersion.V1_0_0);
    assertFalse(reportWithWarning.getWarnings().isEmpty());
    // no ignored rules
    assertEquals(5, reportWithWarning.getWarnings().size());
    reportWithWarning
        .getWarnings()
        .forEach(
            warning -> {
              assertNotNull(warning.getRule());
              assertNotNull(warning.getPath());
              assertNotNull(warning.getTest());
              assertNotNull(warning.getType());
            });
  }

  @Test
  void validateErrorDetails_de() throws IOException {
    String eformsWithError = readFromEFormsResourceAsString(NOTICE_CN_DE_11_WARNING_AND_ERROR);

    ValidationResult validationResult =
        schematronValidator.validate(SupportedType.DE, eformsWithError, SupportedVersion.V1_1_0);
    assertFalse(validationResult.getErrors().isEmpty());
    assertNotEquals(6, validationResult.getErrors().size());
    validationResult
        .getErrors()
        .forEach(
            error -> {
              assertNotNull(error.getRule());
              assertNotNull(error.getPath());
              assertNotNull(error.getTest());
              assertNotNull(error.getType());
            });

    assertTrue(
        validationResult.getErrors().stream()
            .noneMatch(
                error ->
                    error.getRule().equals("BR-BT-00738-0053")
                        || error.getRule().equals("BR-BT-00005-0150")));

    assertTrue(
        validationResult.getErrors().stream()
            .allMatch(
                error ->
                    error.getRule().contains("CR-DE-BT-23")
                        || error.getRule().contains("SR-DE-26")));

    assertFalse(validationResult.getWarnings().isEmpty());
    assertEquals(1, validationResult.getWarnings().size());
    validationResult
        .getWarnings()
        .forEach(warn -> assertTrue(warn.getRule().contains("BR-BT-00514-0304")));
  }

  @Test
  void validateErrorDetails_de_v1_0() throws IOException {
    String eformsWithError = readFromEFormsResourceAsString(NOTICE_CN_DE_10);

    ValidationResult validationResult =
        schematronValidator.validate(SupportedType.DE, eformsWithError, SupportedVersion.V1_0_1);
    assertFalse(validationResult.getErrors().isEmpty());
    validationResult
        .getErrors()
        .forEach(
            error -> {
              assertNotNull(error.getRule());
              assertNotNull(error.getPath());
              assertNotNull(error.getTest());
              assertNotNull(error.getType());
            });
    assertTrue(
        validationResult.getErrors().stream()
            .noneMatch(
                error ->
                    error.getRule().equals("BR-BT-00738-0053")
                        || error.getRule().equals("BR-BT-00005-0150")));
    assertTrue(
        validationResult.getErrors().stream()
            .noneMatch(error -> error.getRule().equals("SR-DE-24")));
    // publication date related rules are triggerred
    assertTrue(
        validationResult.getErrors().stream()
            .anyMatch(error -> error.getRule().contains("SR-DE-26")));
  }

  @Test
  void validateDeSchematronPhase_ignoredVersionValidation() throws IOException {
    String eformsWithError = readFromEFormsResourceAsString(NOTICE_SDK_1_5);

    ValidationResult result = schematronValidator.validate(SupportedType.DE, eformsWithError, SupportedVersion.V1_1_0);

    assertTrue(result.getErrors().isEmpty());
    assertTrue(result.getWarnings().isEmpty());
  }

  private String readFromEFormsResourceAsString(String fileName) throws IOException {
    return Files.readString(
        Path.of(String.format("src/test/resources/eforms/%s", fileName)).toAbsolutePath());
  }
}
