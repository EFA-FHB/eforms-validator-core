package com.nortal.efafhb.eforms.validator.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.validation.profiles.PhaxValidatorIgnoredRulesProfile;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(PhaxValidatorIgnoredRulesProfile.class)
class PhaxValidatorIgnoredRulesTest {

  private static final String CN_24_MAXIMAL_XML_ERROR = "cn_24_maximal_error.xml";
  private static final String CN_24_MINIMAL_XML = "cn_24_minimal.xml";
  private static final String NOTICE_CN_DE_11_WARNING_AND_ERROR =
      "notice_cn_de_11_warning_and_error.xml";

  @Inject FormsValidator schematronValidator;

  @Test
  void validateErrorDetails_ignoredRules() throws IOException {
    String eformsWithError = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML_ERROR);

    ValidationResult reportWithError =
        schematronValidator.validate(SupportedType.EU, eformsWithError, SupportedVersion.V1_0_0);
    assertFalse(reportWithError.getErrors().isEmpty());
    assertNotEquals(2, reportWithError.getErrors().size());
    reportWithError
        .getErrors()
        .forEach(
            error -> {
              assertNotNull(error.getRule());
              assertNotEquals("BR-BT-00506-0052", error.getRule());
              assertNotNull(error.getPath());
              assertNotNull(error.getTest());
              assertNotNull(error.getType());
            });

    String eformsWithWarning = readFromEFormsResourceAsString(CN_24_MINIMAL_XML);
    ValidationResult reportWithWarning =
        schematronValidator.validate(SupportedType.EU, eformsWithWarning, SupportedVersion.V1_0_0);
    assertFalse(reportWithWarning.getWarnings().isEmpty());
    assertNotEquals(5, reportWithWarning.getWarnings().size());
    reportWithWarning
        .getWarnings()
        .forEach(
            warning -> {
              assertNotNull(warning.getRule());
              assertNotEquals("BR-OPT-00301-1180", warning.getRule());
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
              assertTrue(error.getRule().contains("CR-DE-BT-23"));
              assertNotNull(error.getPath());
              assertNotNull(error.getTest());
              assertNotNull(error.getType());
            });

    assertTrue(validationResult.getWarnings().isEmpty());
  }

  private String readFromEFormsResourceAsString(String fileName) throws IOException {
    return Files.readString(
        Path.of(String.format("src/test/resources/eforms/%s", fileName)).toAbsolutePath());
  }
}
