package com.nortal.efafhb.eforms.validator.internal.rest;

import com.nortal.efafhb.eforms.validator.common.Constants;
import com.nortal.efafhb.eforms.validator.exception.ErrorCode;
import com.nortal.efafhb.eforms.validator.exception.ErrorModel;
import com.nortal.efafhb.eforms.validator.validation.ValidatorService;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationModelDTO;
import com.nortal.efafhb.eforms.validator.validation.dto.ValidationRequestDTO;
import java.util.Locale;
import java.util.ResourceBundle;
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
    if (validatorRequestDTO.isValidationDisabled()) {
      return getDisabledValidationsResponse();
    }
    ValidationRequestDTO validationRequestDTO = convertToValidationRequestDTO(validatorRequestDTO);
    ValidationModelDTO validationModelDTO = validatorService.validate(validationRequestDTO);
    return Response.ok(validationModelDTO).build();
  }

  private ValidationRequestDTO convertToValidationRequestDTO(
      ValidatorRequestDTO validatorRequestDTO) {
    return ValidationRequestDTO.builder()
        .sdkType(validatorRequestDTO.getSdkType())
        .version(validatorRequestDTO.getVersion())
        .eforms(validatorRequestDTO.getEforms())
        .xsdValidation(validatorRequestDTO.xsdValidationRequired())
        .schematronValidation(validatorRequestDTO.schematronValidationRequired())
        .build();
  }

  private Response getDisabledValidationsResponse() {
    ResourceBundle resourceBundle =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE));
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(
            ErrorModel.builder()
                .reason(ErrorCode.VALIDATION_DISABLED)
                .description(resourceBundle.getString(ErrorCode.VALIDATION_DISABLED.name()))
                .build())
        .build();
  }
}
