package com.nortal.efafhb.eforms.validator.internal.rest;

import com.nortal.efafhb.eforms.validator.validation.ValidatorService;
import com.nortal.efafhb.eforms.validator.validation.dto.BusinessDocumentValidationRequestDTO;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("v1/validation-business-document")
public class BusinessDocumentValidationEndpoint {

  @Inject ValidatorService validatorService;

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response validateBusinessDocument(
      @MultipartForm @Valid BusinessDocumentValidatorRequestDTO validatorRequestDTO) {
    BusinessDocumentValidationRequestDTO validationRequestDTO =
        convertToBusinessDocumentValidationRequestDTO(validatorRequestDTO);
    return Response.ok(validatorService.validateBusinessDocument(validationRequestDTO)).build();
  }

  private BusinessDocumentValidationRequestDTO convertToBusinessDocumentValidationRequestDTO(
      BusinessDocumentValidatorRequestDTO validatorRequestDTO) {
    return BusinessDocumentValidationRequestDTO.builder()
        .businessDocument(validatorRequestDTO.getBusinessDocument())
        .build();
  }
}
