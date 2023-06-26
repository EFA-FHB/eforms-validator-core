package com.nortal.efafhb.eforms.validator.internal.rest;

import javax.validation.constraints.NotNull;
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
public class BusinessDocumentValidatorRequestDTO {

  @NotNull(message = "Business document must not be null")
  @FormParam("business_document")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  private byte[] businessDocument;
}
