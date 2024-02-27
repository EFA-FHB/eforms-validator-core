package com.nortal.efafhb.eforms.validator.validation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nortal.efafhb.eforms.validator.enums.InfoLevel;
import com.nortal.efafhb.eforms.validator.enums.ReportType;
import com.nortal.efafhb.eforms.validator.enums.SupportedType;
import com.nortal.efafhb.eforms.validator.enums.SupportedVersion;
import com.nortal.efafhb.eforms.validator.validation.FormsValidator;
import com.nortal.efafhb.eforms.validator.validation.ValidationConfig;
import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationRequestDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationModelEntryDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationRequestDTO;
import com.nortal.efafhb.eforms.validator.validation.entry.ValidationEntry;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@QuarkusTest
class ValidatorServiceImplTest {
  private static final String EFORM_WITH_ERRORS_PATH =
      "src/test/resources/eforms/can_29_with_errors.xml";

  @InjectMocks private ValidatorServiceImpl validatorService;

  @Mock BusinessDocumentValidator businessDocumentValidator;

  @Inject ValidationConfig validationConfig;
  @Mock FormsValidator formsValidator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    validatorService =
        new ValidatorServiceImpl(businessDocumentValidator, validationConfig, formsValidator);
  }

  @Test
  void testValidateWithValidationsDisabled() {
    ValidationRequestDTO validationRequestDTO = new ValidationRequestDTO();
    validationRequestDTO.setEforms(new byte[] {});
    validationRequestDTO.setVersion("1.0");
    validationRequestDTO.setSdkType("eforms-de");
    validationRequestDTO.setXsdValidation(false);
    validationRequestDTO.setSchematronValidation(false);

    ValidationModelDTO result = validatorService.validate(validationRequestDTO);
    assertNull(result);
  }

  @Test
  void testValidateWithSchematronValidation() throws IOException {
    ValidationRequestDTO validationRequestDTO = new ValidationRequestDTO();
    validationRequestDTO.setEforms(readEFormAsByteArray(EFORM_WITH_ERRORS_PATH));
    validationRequestDTO.setVersion("1.0");
    validationRequestDTO.setSdkType("eforms-de");
    validationRequestDTO.setXsdValidation(false);
    validationRequestDTO.setSchematronValidation(true);

    ValidationResult result = getValidationResult();
    Mockito.when(
            formsValidator.validate(
                Mockito.any(SupportedType.class),
                Mockito.anyString(),
                Mockito.any(SupportedVersion.class),
                "version"))
        .thenReturn(result);
    ValidationModelDTO validationModel = validatorService.validate(validationRequestDTO);

    assertNotNull(validationModel);
    assertFalse(validationModel.getValid());
    assertEquals(1, validationModel.getWarnings().size());
    assertEquals(1, validationModel.getErrors().size());
  }

  @Test
  void testValidateBusinessDocument() {
    BusinessDocumentValidationRequestDTO requestDTO = new BusinessDocumentValidationRequestDTO();
    requestDTO.setBusinessDocument(new byte[] {});

    when(businessDocumentValidator.validate(any())).thenReturn(new ValidationResult());

    BusinessDocumentValidationModelDTO result =
        validatorService.validateBusinessDocument(requestDTO);
    assertNotNull(result);
    assertTrue(result.getValid());
    assertTrue(result.getErrors().isEmpty());
    assertTrue(result.getWarnings().isEmpty());
  }

  @Test
  void testValidateNoticeWithErros() throws IOException {
    String expectedDescription =
        "Die eForms XSD-Validierung ist fehlgeschlagen aufgrund von: 'cvc-complex-type.2.4.a: "
            + "Invalid content was found starting with element "
            + "'{\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\":TenderingTerms}'. One of "
            + "'{\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\":AdditionalNoticeLanguage,"
            + " \"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\":PreviousDocumentReference, "
            + "\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\":MinutesDocumentReference, "
            + "\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\":Signature, "
            + "\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\":ContractingParty}' "
            + "is expected.'";

    ValidationRequestDTO validationRequestDTO = new ValidationRequestDTO();
    validationRequestDTO.setEforms(readEFormAsByteArray(EFORM_WITH_ERRORS_PATH));
    validationRequestDTO.setVersion("1.0");
    validationRequestDTO.setSdkType("eforms-de");
    validationRequestDTO.setXsdValidation(true);
    validationRequestDTO.setSchematronValidation(false);

    ValidationModelDTO result = validatorService.validate(validationRequestDTO);

    assertNotNull(result);
    assertFalse(result.getValid());
    assertTrue(result.getWarnings().isEmpty());
    assertEquals(1, result.getErrors().size());

    ValidationModelEntryDTO error = result.getErrors().stream().findFirst().get();
    assertEquals("XSD", error.getType());
    assertEquals(expectedDescription, error.getDescription());
    assertNull(error.getRule());
    assertNull(error.getRuleContent());
    assertNull(error.getPath());
  }

  private byte[] readEFormAsByteArray(String fileName) throws IOException {
    return Files.readAllBytes(Paths.get(fileName));
  }

  private static ValidationResult getValidationResult() {
    ValidationResult result = new ValidationResult();
    result.setSdkValidationVersion("eforms-de-1.0");
    ValidationEntry errorEntry = ValidationEntry.builder().build();
    ValidationEntry warningEntry = ValidationEntry.builder().build();
    result.addValidationToReport(ReportType.SCHEMATRON, InfoLevel.ERROR, errorEntry);
    result.addValidationToReport(ReportType.SCHEMATRON, InfoLevel.WARNING, warningEntry);
    return result;
  }
}
