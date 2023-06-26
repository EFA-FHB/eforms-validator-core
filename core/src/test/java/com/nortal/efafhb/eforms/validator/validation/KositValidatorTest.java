package com.nortal.efafhb.eforms.validator.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nortal.efafhb.eforms.validator.validation.output.ValidationResult;
import com.nortal.efafhb.eforms.validator.validation.profiles.KositValidatorProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(KositValidatorProfile.class)
class KositValidatorTest {

  private static final String CN_24_MAXIMAL_XML = "cn_24_maximal.xml";
  private static final String CN_24_MAXIMAL_XML_ERROR = "cn_24_maximal_error.xml";
  private static final String CN_24_MINIMAL_XML = "cn_24_minimal.xml";
  @Inject Validator kositValidator;

  @Test
  void validate() throws IOException {
    String eformsMax = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML);
    String eformsMaxWithError = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML_ERROR);
    String eformsMin = readFromEFormsResourceAsString(CN_24_MINIMAL_XML);
    ValidationResult reportMax =
        kositValidator.validate(SupportedType.EU, eformsMax, SupportedVersion.V1_0_0);
    assertEquals(0, reportMax.getWarnings().size());
    assertEquals(0, reportMax.getErrors().size());

    ValidationResult reportMaxWithError =
        kositValidator.validate(SupportedType.EU, eformsMaxWithError, SupportedVersion.V1_0_0);
    assertEquals(0, reportMaxWithError.getWarnings().size());
    assertNotEquals(0, reportMaxWithError.getErrors().size());

    ValidationResult reportMin =
        kositValidator.validate(SupportedType.EU, eformsMin, SupportedVersion.V1_0_0);
    assertNotEquals(0, reportMin.getWarnings().size());
    assertEquals(0, reportMin.getErrors().size());
  }

  @Test
  void validateErrorDetails() throws IOException {
    String eformsWithError = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML_ERROR);
    String eformsWithWarning = readFromEFormsResourceAsString(CN_24_MINIMAL_XML);

    ValidationResult reportWithError =
        kositValidator.validate(SupportedType.EU, eformsWithError, SupportedVersion.V1_0_0);
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
        kositValidator.validate(SupportedType.EU, eformsWithWarning, SupportedVersion.V1_0_0);
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

  @Test
  void generate_xml_report() throws IOException {
    String eformsMax = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML);
    ValidationResult reportMax =
        kositValidator.validate(SupportedType.EU, eformsMax, SupportedVersion.V1_0_0);
    assertNotNull(reportMax.getXmlReport());
  }

  private String readFromEFormsResourceAsString(String fileName) throws IOException {
    return Files.readString(
        Path.of("src/test/resources/eforms/%s".formatted(fileName)).toAbsolutePath());
  }
}
