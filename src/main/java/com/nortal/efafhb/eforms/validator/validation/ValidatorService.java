package com.nortal.efafhb.eforms.validator.validation;

import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationRequestDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationRequestDTO;

public interface ValidatorService {


  /**
   * Validates the specified business document against the validation rules.
   *
   * @param validationRequestDTO the validation request DTO containing the business document and validation parameters
   * @return the validation model DTO containing the validation results
   */
  ValidationModelDTO validate(ValidationRequestDTO validationRequestDTO);

  /**
   * Validates the specified business document against the business document validation rules.
   *
   * @param validationRequestDTO the business document validation request DTO containing the business document and validation parameters
   * @return the business document validation model DTO containing the validation results
   */
  BusinessDocumentValidationModelDTO validateBusinessDocument(
      BusinessDocumentValidationRequestDTO validationRequestDTO);
}
