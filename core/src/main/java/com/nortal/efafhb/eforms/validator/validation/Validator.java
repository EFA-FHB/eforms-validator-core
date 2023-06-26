package com.nortal.efafhb.eforms.validator.validation;

import com.nortal.efafhb.eforms.validator.validation.output.ValidationResult;

interface Validator {

  /**
   * Validates EForms against schematron rules
   *
   * @param supportedType what version of sdk will validator use
   * @param eforms EForms to be validated
   * @param eformsVersion Specifies against which version of sdk schematrons version to validate
   * @return result of validation
   */
  ValidationResult validate(
      SupportedType supportedType, String eforms, SupportedVersion eformsVersion);
}
