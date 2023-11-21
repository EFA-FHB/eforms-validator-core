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
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
  private static final String ISSUE_DATE_XML_TAG = "cbc:IssueDate";
  private static final String REQUESTED_PUBLICATION_DATE_XML_TAG = "cbc:RequestedPublicationDate";
  private static final String CONTRACT_NOTICE_XML_TAG = "ContractNotice";
  private static final String CONTRACT_AWARD_NOTICE_XML_TAG = "ContractAwardNotice";
  private static final String TENDER_SUBMISSION_DEADLINE_PERIOD_XML_TAG =
      "cac:TenderSubmissionDeadlinePeriod";
  private static final String END_DATE_XML_TAG = "cbc:EndDate";
  private static final String OPEN_TENDER_EVENT_XML_TAG = "cac:OpenTenderEvent";
  private static final String OCCURRENCE_DATE_XML_TAG = "cbc:OccurrenceDate";

  private static final String DATE_PATTERN = "yyyy-MM-dd+02:00";
  private static final String DATE_PATTERN_Z = "yyyy-MM-dd'Z'";

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
    String eFormsWithError = readFromEFormsResourceAsString(CN_24_MAXIMAL_XML_ERROR);
    String eformsWithWarning = readFromEFormsResourceAsString(CN_24_MINIMAL_XML);

    ValidationResult reportWithError =
        schematronValidator.validate(SupportedType.EU, eFormsWithError, SupportedVersion.V1_0_0);
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
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_CN_DE_11_WARNING_AND_ERROR);

    ValidationResult validationResult =
        schematronValidator.validate(SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0);
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
  void validateErrorDetails_de_fixed_dates_to_current()
      throws IOException, ParserConfigurationException, TransformerException, SAXException {
    String eFormsWithErrorUri =
        getEformsAbsolutePath(NOTICE_CN_DE_11_WARNING_AND_ERROR).toUri().toString();

    String eFormsWithError =
        replaceDateTagsToCurrentDate(
            eFormsWithErrorUri,
            new DateTags(ISSUE_DATE_XML_TAG, CONTRACT_AWARD_NOTICE_XML_TAG, 1L),
            new DateTags(REQUESTED_PUBLICATION_DATE_XML_TAG, CONTRACT_AWARD_NOTICE_XML_TAG, 1L));

    ValidationResult validationResult =
        schematronValidator.validate(SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0);
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
        schematronValidator.validate(SupportedType.DE, eFormsWithError, SupportedVersion.V1_0_1);
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
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_CN_DE_11);

    ValidationResult validationResult =
        schematronValidator.validate(SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0);
    assertTrue(validationResult.getErrors().isEmpty());
    assertTrue(validationResult.getWarnings().isEmpty());
  }

  @Test
  void validateErrorDetails_de_v1_1_fixed_dates_to_current()
      throws IOException, ParserConfigurationException, TransformerException, SAXException {

    String eFormsWithErrorUri = getEformsAbsolutePath(NOTICE_CN_DE_11).toUri().toString();
    String eFormsWithError =
        replaceDateTagsToCurrentDate(
            eFormsWithErrorUri,
            new DateTags(ISSUE_DATE_XML_TAG, CONTRACT_NOTICE_XML_TAG, 1L),
            new DateTags(REQUESTED_PUBLICATION_DATE_XML_TAG, CONTRACT_NOTICE_XML_TAG, 1L),
            new DateTags(
                END_DATE_XML_TAG,
                TENDER_SUBMISSION_DEADLINE_PERIOD_XML_TAG,
                0L,
                true,
                DATE_PATTERN),
            new DateTags(
                OCCURRENCE_DATE_XML_TAG, OPEN_TENDER_EVENT_XML_TAG, 0L, true, DATE_PATTERN_Z));

    ValidationResult validationResult =
        schematronValidator.validate(SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0);
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
      String eFormsWithError = readFromEFormsResourceAsString(validNotice);

      ValidationResult result =
          schematronValidator.validate(SupportedType.EU, eFormsWithError, SupportedVersion.V1_5_5);

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
          schematronValidator.validate(SupportedType.EU, eFormsWithError, SupportedVersion.V1_5_5);

      assertFalse(result.getErrors().isEmpty());
    }
  }

  @Test
  void validateDeSchematronPhase_ignoredVersionValidation() throws IOException {
    String eFormsWithError = readFromEFormsResourceAsString(NOTICE_SDK_1_5);

    ValidationResult result =
        schematronValidator.validate(SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0);

    assertEquals(1, result.getErrors().size());

    assertTrue(result.getErrors().stream().allMatch(error -> error.getRule().contains("SR-DE-1")));
    assertTrue(result.getWarnings().isEmpty());
  }

  @Test
  void validateDeSchematronPhase_ignoredVersionValidation_fixed_dates_to_current()
      throws IOException, ParserConfigurationException, TransformerException, SAXException {
    String eFormsWithErrorUri = getEformsAbsolutePath(NOTICE_SDK_1_5).toUri().toString();

    String eFormsWithError =
        replaceDateTagsToCurrentDate(
            eFormsWithErrorUri,
            new DateTags(ISSUE_DATE_XML_TAG, CONTRACT_NOTICE_XML_TAG, 1L),
            new DateTags(REQUESTED_PUBLICATION_DATE_XML_TAG, CONTRACT_NOTICE_XML_TAG, 1L),
            new DateTags(
                END_DATE_XML_TAG,
                TENDER_SUBMISSION_DEADLINE_PERIOD_XML_TAG,
                0L,
                true,
                DATE_PATTERN),
            new DateTags(
                OCCURRENCE_DATE_XML_TAG, OPEN_TENDER_EVENT_XML_TAG, 0L, true, DATE_PATTERN_Z));

    ValidationResult result =
        schematronValidator.validate(SupportedType.DE, eFormsWithError, SupportedVersion.V1_1_0);

    assertEquals(1, result.getErrors().size());

    assertTrue(result.getErrors().stream().allMatch(error -> error.getRule().contains("SR-DE-1")));
    assertTrue(result.getWarnings().isEmpty());
  }

  private String readFromEFormsResourceAsString(String fileName) throws IOException {
    return Files.readString(getEformsAbsolutePath(fileName));
  }

  private Path getEformsAbsolutePath(String fileName) {
    return Path.of(String.format("src/test/resources/eforms/%s", fileName)).toAbsolutePath();
  }

  private static String replaceDateTagsToCurrentDate(String eFormsFileUri, DateTags... xmlTags)
      throws ParserConfigurationException, IOException, SAXException, TransformerException {

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document document = dBuilder.parse(eFormsFileUri);
    document.getDocumentElement().normalize();

    for (DateTags xmlTag : xmlTags) {
      changeDateTagValue(xmlTag, document);
    }

    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    StringWriter writer = new StringWriter();
    transformer.transform(new DOMSource(document), new StreamResult(writer));
    return writer.getBuffer().toString();
  }

  private static void changeDateTagValue(DateTags xmlTag, Document document) {
    NodeList tagNameList = document.getElementsByTagName(xmlTag.xmlTag);

    for (int i = 0; i < tagNameList.getLength(); i++) {
      Element element = (Element) tagNameList.item(i);
      if (element.getParentNode().getNodeName().equals(xmlTag.parentXmlTag)) {
        element
            .getFirstChild()
            .setNodeValue(getNewDateTimeTagValue(xmlTag.minusDays, xmlTag.datePattern));
        if (!xmlTag.changeAll) {
          break;
        }
      }
    }
  }

  private static String getNewDateTimeTagValue(long minusDays, String pattern) {
    return LocalDate.now().minusDays(minusDays).format(DateTimeFormatter.ofPattern(pattern));
  }

  private static class DateTags {

    String xmlTag;
    String parentXmlTag;
    long minusDays;
    boolean changeAll;
    String datePattern;

    public DateTags(String xmlTag, String parentXmlTag, long minusDays) {
      this(xmlTag, parentXmlTag, minusDays, false, DATE_PATTERN);
    }

    public DateTags(
        String xmlTag, String parentXmlTag, long minusDays, boolean changeAll, String datePattern) {

      this.xmlTag = xmlTag;
      this.parentXmlTag = parentXmlTag;
      this.minusDays = minusDays;
      this.changeAll = changeAll;
      this.datePattern = datePattern;
    }
  }
}
