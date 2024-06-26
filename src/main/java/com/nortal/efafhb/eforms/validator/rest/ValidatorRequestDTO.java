package com.nortal.efafhb.eforms.validator.rest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
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
  private @Pattern(regexp = "^[\\d]\\.(\\d+)$", message = "BAD_EFORM_VERSION_FORMAT") String
      version;

  @NotNull(message = "Sdk type must not be null.")
  @FormParam("sdkType")
  private String sdkType;

  @NotNull(message = "EForms must not be null")
  @FormParam("eforms")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  private byte[] eforms;
}
