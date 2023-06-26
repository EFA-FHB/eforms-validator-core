package com.nortal.efafhb.eforms.validator.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nortal.efafhb.eforms.validator.validation.output.ValidationResult;
import com.nortal.efafhb.eforms.validator.validation.profiles.PhaxValidatorProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(PhaxValidatorProfile.class)
class PhaxValidatorTest {

  private static final String CN_24_MAXIMAL_XML_ERROR = "cn_24_maximal_error.xml";
  private static final String CN_24_MINIMAL_XML = "cn_24_minimal.xml";
  private static final String CN_24_MINIMAL_V_0_1_XML = "cn_24_minimal_V0.1.xml";

  @Inject Validator schematronValidator;

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
    assertNotEquals(0, reportWithError.getErrors().size());
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
    assertNotEquals(0, reportWithWarning.getWarnings().size());
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

  private String readFromEFormsResourceAsString(String fileName) throws IOException {
    return Files.readString(
        Path.of("src/test/resources/eforms/%s".formatted(fileName)).toAbsolutePath());
  }
}
