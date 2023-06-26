package com.nortal.efafhb.eforms.validator.internal.rest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidatorRequestDTO {

  @NotNull(message = "Version must not be null")
  @FormParam("eformsVersion")
  private @Pattern(regexp = "^[\\d]\\.[\\d]$", message = "BAD_EFORM_VERSION_FORMAT") String version;

  @NotNull(message = "Sdk type must not be null.")
  @FormParam("sdkType")
  private @Pattern(regexp = "^eforms-(eu|de|EU|DE)$", message = "INVALID_SDK_TYPE") String sdkType;

  @NotNull(message = "EForms must not be null")
  @FormParam("eforms")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  private byte[] eforms;

  @FormParam("xsdValidation")
  private @Pattern(regexp = "^$|^(true|false)$", message = "INVALID_XSD_VALIDATION_FLAG_VALUE")
  String xsdValidation;

  @FormParam("schematronValidation")
  private @Pattern(
      regexp = "^$|^(true|false)$",
      message = "INVALID_SCHEMATRON_VALIDATION_FLAG_VALUE") String schematronValidation;

  public boolean xsdValidationRequired() {
    return xsdValidation == null || Boolean.parseBoolean(xsdValidation);
  }

  public boolean schematronValidationRequired() {
    return schematronValidation == null || Boolean.parseBoolean(schematronValidation);
  }

  public boolean isValidationDisabled() {
    return !this.schematronValidationRequired() && !this.xsdValidationRequired();
  }
}
