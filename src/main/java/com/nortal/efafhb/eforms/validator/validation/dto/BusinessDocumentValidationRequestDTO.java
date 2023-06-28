package com.nortal.efafhb.eforms.validator.validation.dto;

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
public class BusinessDocumentValidationRequestDTO {
  private byte[] businessDocument;
}
