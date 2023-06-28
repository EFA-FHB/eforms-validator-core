package com.nortal.efafhb.eforms.validator.validation.dto;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDocumentValidationModelDTO implements Serializable {

  private Boolean valid;
  private Set<ValidationModelEntryDTO> warnings;
  private Set<ValidationModelEntryDTO> errors;
}
