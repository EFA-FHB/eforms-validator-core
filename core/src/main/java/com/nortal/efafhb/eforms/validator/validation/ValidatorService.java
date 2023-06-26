package com.nortal.efafhb.eforms.validator.validation;

import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationRequestDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationRequestDTO;

public interface ValidatorService {

  ValidationModelDTO validate(ValidationRequestDTO validationRequestDTO);

  BusinessDocumentValidationModelDTO validateBusinessDocument(
      BusinessDocumentValidationRequestDTO validationRequestDTO);
}
