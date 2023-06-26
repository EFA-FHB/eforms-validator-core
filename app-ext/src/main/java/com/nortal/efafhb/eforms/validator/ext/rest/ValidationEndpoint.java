package com.nortal.efafhb.eforms.validator.ext.rest;

import com.nortal.efafhb.eforms.validator.validation.ValidatorService;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationRequestDTO;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("v1/validation")
public class ValidationEndpoint {

  @Inject ValidatorService validatorService;

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response validate(@MultipartForm @Valid ValidatorRequestDTO validatorRequestDTO) {
    ValidationRequestDTO validationRequestDTO = convertToValidationRequestDTO(validatorRequestDTO);
    return Response.ok(validatorService.validate(validationRequestDTO)).build();
  }

  private ValidationRequestDTO convertToValidationRequestDTO(
      ValidatorRequestDTO validatorRequestDTO) {
    return ValidationRequestDTO.builder()
        .sdkType(validatorRequestDTO.getSdkType())
        .version(validatorRequestDTO.getVersion())
        .eforms(validatorRequestDTO.getEforms())
        .build();
  }
}
