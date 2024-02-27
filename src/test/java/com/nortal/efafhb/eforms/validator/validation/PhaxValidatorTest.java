package com.nortal.efafhb.eforms.validator.validation;

import static com.nortal.efafhb.eforms.validator.validation.PhaxValidatorDoePhaseTest.CONTRACT_AWARD_NOTICE_XML_TAG;
import static com.nortal.efafhb.eforms.validator.validation.PhaxValidatorDoePhaseTest.CONTRACT_NOTICE_XML_TAG;
import static com.nortal.efafhb.eforms.validator.validation.PhaxValidatorDoePhaseTest.ISSUE_DATE_XML_TAG;
import static com.nortal.efafhb.eforms.validator.validation.PhaxValidatorDoePhaseTest.PLANNED_DATE_XML_TAG;
import static com.nortal.efafhb.eforms.validator.validation.PhaxValidatorDoePhaseTest.REQUESTED_PUBLICATION_DATE_XML_TAG;
import static com.nortal.efafhb.eforms.validator.validation.TestUtils.DATE_PATTERN;
import static com.nortal.efafhb.eforms.validator.validation.TestUtils.getEformsAbsolutePath;
import static com.nortal.efafhb.eforms.validator.validation.TestUtils.readFromEFormsResourceAsString;
import static com.nortal.efafhb.eforms.validator.validation.TestUtils.replaceDateTagsToCurrentDate;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.validation.TestUtils.DateTags;
import com.nortal.efafhb.eforms.validator.validation.entry.ValidationEntry;
import com.nortal.efafhb.eforms.validator.validation.profiles.PhaxValidatorProfile;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@QuarkusTest
@TestProfile(PhaxValidatorProfile.class)
class PhaxValidatorTest {

  private static final String CN_24_MAXIMAL_XML_ERROR = "cn_24_maximal_error.xml";
  private static final String CN_24_MINIMAL_XML = "cn_24_minimal.xml";
  private static final String CN_24_MINIMAL_V_0_1_XML = "cn_24_minimal_V0.1.xml";
  private static final String NOTICE_CN_DE_11_WARNING_AND_ERROR =
      "notice_cn_de_11_warning_and_error.xml";
  private static final String NOTICE_CN_DE_10 = "notice_cn_de_10.xml";
  private static final String NOTICE_CN_DE_11 = "eforms_CN_16_max-DE_valid.xml";
  private static final String NOTICE_SDK_1_5 = "eform-sdk-1.5.xml";
  private static final String NOTICE_SDK_1_10_T01 = "t01_PRT.xml";
  private static final String NOTICE_SDK_1_10_T02 = "t02_ESP.xml";
  public static final String END_DATE_XML_TAG = "cbc:EndDate";
  public static final String TENDER_SUBMISSION_XML_TAG = "cac:TenderSubmissionDeadlinePeriod";
  public static final String PRIOR_INFORMATION_NOTICE_XML_TAG = "PriorInformationNotice";

  @Inject FormsValidator schematronValidator;

  @Test
  void validate() throws IOException {
    String eFormsMaxWithError = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML_ERROR);
    String eFormsMin = readFromEFormsResourceAsString(CN_24_MINIMAL_XML);
    String eFormsV01 = readFromEFormsResourceAsString(CN_24_MINIMAL_V_0_1_XML);

    ValidationResult reportMaxWithError =
        schematronValidator.validate(
            SupportedType.EU, eFormsMaxWithError, SupportedVersion.V1_0_0, "version");
    assertEquals(0, reportMaxWithError.getWarnings().size());
    assertNotEquals(0, reportMaxWithError.getErrors().size());

    ValidationResult reportMin =
        schematronValidator.validate(
            SupportedType.EU, eFormsMin, SupportedVersion.V1_0_0, "version");
    assertNotEquals(0, reportMin.getWarnings().size());
    assertEquals(0, reportMin.getErrors().size());

    ValidationResult reportWrongVersion =
        schematronValidator.validate(
            SupportedType.EU, eFormsV01, SupportedVersion.V1_0_0, "version");
    assertEquals(0, reportWrongVersion.getWarnings().size());
    assertNotEquals(0, reportWrongVersion.getErrors().size());

    ValidationResult reportMinVersion01 =
        schematronValidator.validate(
            SupportedType.EU, eFormsV01, SupportedVersion.V0_1_1, "version");
    assertEquals(0, reportMinVersion01.getWarnings().size());
    assertEquals(0, reportMinVersion01.getErrors().size());
  }

  @Test
  void validateErrorDetails() throws IOException {
    String eFormsWithError = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML_ERROR);
    String eFormsWithWarning = readFromEFormsResourceAsString(CN_24_MINIMAL_XML);

    ValidationResult reportWithError =
        schematronValidator.validate(
            SupportedType.EU, eFormsWithError, SupportedVersion.V1_0_0, "version");
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
        schematronValidator.validate(
            SupportedType.EU, eFormsWithWarning, SupportedVersion.V1_0_0, "version");
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
    String eFormWithErrorAndWarning =
        readFromEFormsResourceAsString(NOTICE_CN_DE_11_WARNING_AND_ERROR);

    ValidationResult validationResult =
        schematronValidator.validate(
            SupportedType.DE, eFormWithErrorAndWarning, SupportedVersion.V1_1_0, "version");
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
                        || error.getRule().contains("SR-DE-26")
                        || error
                            .getRule()
                            .contains(
                                "Date of BT-05=2023-05-23+02:00 must be less than 1 day in the past")));

    assertFalse(validationResult.getWarnings().isEmpty());
    assertEquals(1, validationResult.getWarnings().size());
    validationResult
        .getWarnings()
        .forEach(warn -> assertTrue(warn.getRule().contains("BR-BT-00514-0304")));
  }

  @Test
  void validateErrorDetails_de_fixed_dates_to_current() throws IOException {
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_CN_DE_11_WARNING_AND_ERROR);

    ValidationResult validationResult =
        schematronValidator.validate(
            SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0, "version");
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
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_CN_DE_10);

    ValidationResult validationResult =
        schematronValidator.validate(
            SupportedType.DE, eFormsWithError, SupportedVersion.V1_0_1, "version");
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
  void validateErrorDetails_de_v1_1() throws IOException {
    String eForms = readFromEFormsResourceAsString(NOTICE_CN_DE_11);

    ValidationResult validationResult =
        schematronValidator.validate(SupportedType.DE, eForms, SupportedVersion.V1_1_0, "version");
    assertTrue(validationResult.getErrors().isEmpty());
    assertTrue(validationResult.getWarnings().isEmpty());
  }

  @Test
  void validateErrorDetails_de_v1_1_fixed_dates_to_current() throws IOException {
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_CN_DE_11);

    ValidationResult validationResult =
        schematronValidator.validate(
            SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0, "version");
    assertTrue(validationResult.getErrors().isEmpty());
    assertTrue(validationResult.getWarnings().isEmpty());
  }

  @Test
  void validate_notices_eu_v1_5_5_valid() throws IOException {
    List<String> validNotices =
        List.of(
            "1.5.5/valid/can_24_maximal.xml",
            "1.5.5/valid/cn_24_maximal.xml",
            "1.5.5/valid/pin-buyer_24_published.xml");

    for (String validNotice : validNotices) {
      String eForm = readFromEFormsResourceAsString(validNotice);

      ValidationResult result =
          schematronValidator.validate(SupportedType.EU, eForm, SupportedVersion.V1_5_5, "version");

      assertTrue(result.getErrors().isEmpty());
      assertTrue(result.getWarnings().isEmpty());
    }
  }

  @Test
  void validate_notices_eu_v1_5_5_invalid() throws IOException {
    List<String> invalidNotices =
        List.of(
            "1.5.5/invalid/INVALID_can_24_stage-2.xml",
            "1.5.5/invalid/INVALID_cn_24_stage-2.xml",
            "1.5.5/invalid/INVALID_pin-buyer_24_stage-1.xml");

    for (String validNotice : invalidNotices) {
      String eFormsWithError = readFromEFormsResourceAsString(validNotice);

      ValidationResult result =
          schematronValidator.validate(
              SupportedType.EU, eFormsWithError, SupportedVersion.V1_5_5, "version");

      assertFalse(result.getErrors().isEmpty());
    }
  }

  @Test
  void validateDeSchematronPhase_ignoredVersionValidation() throws IOException {
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_SDK_1_5);

    ValidationResult result =
        schematronValidator.validate(
            SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0, "version");

    assertTrue(result.getErrors().isEmpty());
    assertTrue(result.getWarnings().isEmpty());
  }

  @Test
  void validateDeSchematronPhase_ignoredVersionValidation_fixed_dates_to_current()
      throws IOException {
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_SDK_1_5);

    ValidationResult result =
        schematronValidator.validate(
            SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0, "version");

    assertTrue(result.getErrors().isEmpty());
    assertTrue(result.getWarnings().isEmpty());
  }

  @Test
  void validate_notices_eu_v1_10_1_valid() throws Exception {
    List<String> validNotices =
        List.of(
            "1.10.1/valid/can_24_maximal.xml",
            "1.10.1/valid/cn_24_maximal.xml",
            "1.10.1/valid/pin-buyer_24_published.xml");

    for (String validNotice : validNotices) {
      String path = getEformsAbsolutePath(validNotice).toUri().toString();
      String eForm =
          replaceDateTagsToCurrentDate(
              path,
              new DateTags(
                  ISSUE_DATE_XML_TAG, CONTRACT_AWARD_NOTICE_XML_TAG, 0L, true, DATE_PATTERN),
              new DateTags(ISSUE_DATE_XML_TAG, CONTRACT_NOTICE_XML_TAG, 0L, true, DATE_PATTERN),
              new DateTags(
                  ISSUE_DATE_XML_TAG, PRIOR_INFORMATION_NOTICE_XML_TAG, 0L, true, DATE_PATTERN),
              new DateTags(END_DATE_XML_TAG, TENDER_SUBMISSION_XML_TAG, -2L, true, DATE_PATTERN));

      ValidationResult result =
          schematronValidator.validate(
              SupportedType.EU, eForm, SupportedVersion.V1_10_1, "version");

      assertTrue(result.getErrors().isEmpty());
      assertTrue(result.getWarnings().isEmpty());
    }
  }

  @Test
  void validate_notices_eu_v1_10_1_invalid() throws IOException {
    List<String> invalidNotices =
        List.of(
            "1.10.1/invalid/INVALID_can_24_stage-2.xml",
            "1.10.1/invalid/INVALID_cn_24_stage-2.xml",
            "1.10.1/invalid/INVALID_pin-buyer_24_stage-1.xml");

    for (String validNotice : invalidNotices) {
      String eFormsWithError = readFromEFormsResourceAsString(validNotice);

      ValidationResult validationResult =
          schematronValidator.validate(
              SupportedType.EU, eFormsWithError, SupportedVersion.V1_10_1, "version");

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
    }
  }

  @Test
  void validate_notices_t01_invalid() throws IOException {
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_SDK_1_10_T01);

    ValidationResult validationResult =
        schematronValidator.validate(
            SupportedType.EU, eFormsWithError, SupportedVersion.V1_10_1, "version");

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

    boolean containsDispatchDateError =
        validationResult.getErrors().stream()
            .map(ValidationEntry::getRule)
            .anyMatch(
                rule ->
                    rule.equals(
                        "Notice Dispatch Date eSender (BT-803), or by default Notice Dispatch Date (BT-05), must be between 0 and 24 hours before the current date."));
    assertTrue(containsDispatchDateError);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "1.2.0/valid/eforms_CAN_29-DE_valid.xml",
        "1.2.0/valid/eforms_CAN_31-DE_valid_FRA.xml",
        "1.2.0/valid/eforms_PIN_4_min-DE_valid.xml",
        "1.2.0/valid/eforms_PIN_7-DE.xml"
      })
  void validate_notices_de_v1_2_0_valid(String validNotice) throws Exception {
    String path = getEformsAbsolutePath(validNotice).toUri().toString();
    String eForm =
        replaceDateTagsToCurrentDate(
            path,
            new DateTags(
                ISSUE_DATE_XML_TAG,
                "urn:" + PRIOR_INFORMATION_NOTICE_XML_TAG,
                0L,
                true,
                DATE_PATTERN),
            new DateTags(
                REQUESTED_PUBLICATION_DATE_XML_TAG,
                "urn:" + PRIOR_INFORMATION_NOTICE_XML_TAG,
                -2L,
                true,
                DATE_PATTERN),
            new DateTags(
                ISSUE_DATE_XML_TAG, PRIOR_INFORMATION_NOTICE_XML_TAG, 0L, true, DATE_PATTERN),
            new DateTags(
                REQUESTED_PUBLICATION_DATE_XML_TAG,
                PRIOR_INFORMATION_NOTICE_XML_TAG,
                -2L,
                true,
                DATE_PATTERN),
            new DateTags(ISSUE_DATE_XML_TAG, CONTRACT_AWARD_NOTICE_XML_TAG, 0L, true, DATE_PATTERN),
            new DateTags(
                PLANNED_DATE_XML_TAG, PRIOR_INFORMATION_NOTICE_XML_TAG, -1L, true, DATE_PATTERN),
            new DateTags(
                REQUESTED_PUBLICATION_DATE_XML_TAG,
                CONTRACT_AWARD_NOTICE_XML_TAG,
                -2L,
                true,
                DATE_PATTERN));

    ValidationResult result =
        schematronValidator.validate(SupportedType.DE, eForm, SupportedVersion.V1_2_0, "version");
    assertAll(
        () -> assertTrue(result.getErrors().isEmpty()),
        () -> assertTrue(result.getWarnings().isEmpty()));
  }

  @Test
  void validate_notices_t02_invalid() throws IOException {
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_SDK_1_10_T02);

    ValidationResult validationResult =
        schematronValidator.validate(
            SupportedType.EU, eFormsWithError, SupportedVersion.V1_10_1, "version");

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

    boolean containsDispatchDateError =
        validationResult.getErrors().stream()
            .map(ValidationEntry::getRule)
            .anyMatch(
                rule ->
                    rule.equals(
                        "Notice Dispatch Date eSender (BT-803), or by default Notice Dispatch Date (BT-05), must be between 0 and 24 hours before the current date."));
    assertTrue(containsDispatchDateError);
  }

  @Test
  void validate_notices_de_v1_2_0_invalid() throws IOException {
    List<String> invalidNotices =
        List.of(
            "1.2.0/invalid/eforms_CAN_25-DE.xml",
            "1.2.0/invalid/eforms_CAN_26-DE_.xml",
            "1.2.0/invalid/eforms_CAN_27-DE.xml",
            "1.2.0/invalid/eforms_CAN_28-DE.xml");

    for (String validNotice : invalidNotices) {
      String eFormsWithError = readFromEFormsResourceAsString(validNotice);

      ValidationResult validationResult =
          schematronValidator.validate(
              SupportedType.DE, eFormsWithError, SupportedVersion.V1_2_0, "version");

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
    }
  }
}
