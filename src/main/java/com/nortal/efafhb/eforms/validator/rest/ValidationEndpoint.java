package com.nortal.efafhb.eforms.validator.rest;

import com.nortal.efafhb.eforms.validator.validation.ValidatorService;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationRequestDTO;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

/** REST endpoint for validating e-forms using the ValidatorService. */
@Path("v1/eforms-validation")
public class ValidationEndpoint {

  @Inject ValidatorService validatorService;

  /**
   * Endpoint for validating e-forms.
   *
   * @param validatorRequestDTO The request DTO containing e-forms data to be validated.
   * @return A JSON response containing the validation results.
   */
  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response validate(@MultipartForm @Valid ValidatorRequestDTO validatorRequestDTO) {
    ValidationRequestDTO validationRequestDTO = convertToValidationRequestDTO(validatorRequestDTO);
    return Response.ok(validatorService.validate(validationRequestDTO)).build();
  }

  /**
   * Converts the ValidatorRequestDTO to a ValidationRequestDTO.
   *
   * @param validatorRequestDTO The ValidatorRequestDTO to convert.
   * @return The converted ValidationRequestDTO.
   */
  private ValidationRequestDTO convertToValidationRequestDTO(
      ValidatorRequestDTO validatorRequestDTO) {
    return ValidationRequestDTO.builder()
        .sdkType(validatorRequestDTO.getSdkType())
        .version(validatorRequestDTO.getVersion())
        .eforms(validatorRequestDTO.getEforms())
        .build();
  }
}
