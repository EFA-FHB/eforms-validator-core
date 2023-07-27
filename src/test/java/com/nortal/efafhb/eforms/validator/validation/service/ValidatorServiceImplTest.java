package com.nortal.efafhb.eforms.validator.validation.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nortal.efafhb.eforms.validator.validation.FormsValidator;
import com.nortal.efafhb.eforms.validator.validation.ValidationConfig;
import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationRequestDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationRequestDTO;
import com.nortal.efafhb.eforms.validator.validation.util.ValidationResult;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@QuarkusTest
class ValidatorServiceImplTest {

  @InjectMocks private ValidatorServiceImpl validatorService;

  @Mock BusinessDocumentValidator businessDocumentValidator;

  @Inject ValidationConfig validationConfig;
  @Mock FormsValidator formsValidator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    validatorService =
        new ValidatorServiceImpl(businessDocumentValidator, validationConfig, formsValidator);
  }

  @Test
  void testValidateWithSchematronValidation() {
    ValidationRequestDTO validationRequestDTO = new ValidationRequestDTO();
    validationRequestDTO.setSchematronValidation(true);
    validationRequestDTO.setEforms(new byte[] {});
    validationRequestDTO.setVersion("1.0");
    validationRequestDTO.setSdkType("eforms-de");
    validationRequestDTO.setXsdValidation(false);
    validationRequestDTO.setSchematronValidation(false);

    ValidationModelDTO result = validatorService.validate(validationRequestDTO);
    assertNull(result);
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
}
