package com.nortal.efafhb.eforms.validator.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.validation.entry.ValidationEntry;
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
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.IntStream;
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
  private static final String NOTICE_SDK_1_5 = "eform-sdk-1.5.xml";
  private static final String ISSUE_DATE_XML_TAG = "cbc:IssueDate";
  private static final String REQUESTED_PUBLICATION_DATE_XML_TAG = "cbc:RequestedPublicationDate";

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
            .anyMatch(
                error ->
                    error
                        .getRule()
                        .contains(
                            "Notice Dispatch Date must be between 0 and 24 hours "
                                + "before the current date.")));

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
        replaceDateTagToCurrentDate(
            eFormsWithErrorUri, ISSUE_DATE_XML_TAG, REQUESTED_PUBLICATION_DATE_XML_TAG);

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
  void validateDeSchematronPhase_ignoredVersionValidation()
      throws IOException, ParserConfigurationException, TransformerException, SAXException {
    String eformsWithErrorUri = getEformsAbsolutePath(NOTICE_SDK_1_5).toUri().toString();

    String eformsWithError =
        replaceDateTagToCurrentDate(
            eformsWithErrorUri, ISSUE_DATE_XML_TAG, REQUESTED_PUBLICATION_DATE_XML_TAG);
    ValidationResult result =
        schematronValidator.validate(SupportedType.DE, eformsWithError, SupportedVersion.V1_1_0);

    assertTrue(result.getErrors().isEmpty());
    assertTrue(result.getWarnings().isEmpty());
  }

  @Test
  void validateDeSchematronPhase_ignoredVersionValidation_fixed_dates_to_current()
      throws IOException {
    String eformsWithError = readFromEFormsResourceAsString(NOTICE_SDK_1_5);

    ValidationResult result =
        schematronValidator.validate(SupportedType.DE, eformsWithError, SupportedVersion.V1_1_0);

    assertEquals(1, result.getErrors().size());

    ValidationEntry resultValidationEntry = new ArrayList<>(result.getErrors()).get(0);
    assertEquals(
        "Notice Dispatch Date must be between 0 and 24 hours before the current date.",
        resultValidationEntry.getRule());
    assertEquals("/cn:ContractNotice/cbc:IssueDate", resultValidationEntry.getPath());
    assertEquals(
        "Rule: Notice Dispatch Date must be between 0 and 24 hours before the current date. ; Test: ((current-date() - xs:date(text())) le xs:dayTimeDuration('P2D')) and ((current-date() - xs:date(text())) ge xs:dayTimeDuration('-P1D')) ; Location: /cn:ContractNotice/cbc:IssueDate",
        resultValidationEntry.getFormattedMessage());
    assertEquals(
        "((current-date() - xs:date(text())) le xs:dayTimeDuration('P2D')) and ((current-date() - xs:date(text())) ge xs:dayTimeDuration('-P1D'))",
        resultValidationEntry.getTest());
    assertEquals("SCHEMATRON", resultValidationEntry.getType());

    assertTrue(result.getWarnings().isEmpty());
  }

  private String readFromEFormsResourceAsString(String fileName) throws IOException {
    return Files.readString(getEformsAbsolutePath(fileName));
  }

  private Path getEformsAbsolutePath(String fileName) {
    return Path.of(String.format("src/test/resources/eforms/%s", fileName)).toAbsolutePath();
  }

  private static String replaceDateTagToCurrentDate(String eFormsFileUri, String... xmlTags)
      throws ParserConfigurationException, IOException, SAXException, TransformerException {

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document document = dBuilder.parse(eFormsFileUri);
    document.getDocumentElement().normalize();

    for (String xmlTag : xmlTags) {
      changeTagValue(xmlTag, document);
    }

    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    StringWriter writer = new StringWriter();
    transformer.transform(new DOMSource(document), new StreamResult(writer));
    return writer.getBuffer().toString();
  }

  private static void changeTagValue(String xmlTag, Document document) {
    NodeList tagNameList = document.getElementsByTagName(xmlTag);
    Deque<Integer> minusDaysDeque = new LinkedList<>();
    IntStream.rangeClosed(1, tagNameList.getLength()).forEach(minusDaysDeque::push);

    for (int i = 0; i < tagNameList.getLength(); i++) {
      Element element = (Element) tagNameList.item(i);
      element.getFirstChild().setNodeValue(getNewDateTagValue(minusDaysDeque.pop()));
    }
  }

  private static String getNewDateTagValue(long minusDays) {
    return LocalDate.now()
        .minusDays(minusDays)
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd+02:00"));
  }
}
