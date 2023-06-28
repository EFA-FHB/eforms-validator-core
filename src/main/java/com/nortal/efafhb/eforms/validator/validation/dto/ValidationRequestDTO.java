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
public class ValidationRequestDTO {

  private String version;

  private String sdkType;

  private byte[] eforms;

  @Builder.Default private boolean xsdValidation = true;

  @Builder.Default private boolean schematronValidation = true;
}
