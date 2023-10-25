package com.nortal.efafhb.eforms.validator.exception;

import com.nortal.efafhb.eforms.validator.common.Constants;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Locale;
import java.util.ResourceBundle;

@Provider
public class ValidatorApplicationExceptionMapper
    implements ExceptionMapper<ValidatorApplicationException> {

  /**
   * Maps the provided ValidatorApplicationException to a Response object.
   *
   * @param exception the ValidatorApplicationException to be mapped
   * @return a Response object representing the mapped exception
   */
  @Override
  public Response toResponse(ValidatorApplicationException exception) {
    ResourceBundle resourceBundle =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE));
    Response.Status responseStatus =
        (exception.getErrorCode().equals(ErrorCode.MALFORMED_XML)
                || exception.getErrorCode().equals(ErrorCode.UNSUPPORTED_EFORM_VERSION_SDK_TYPE))
            ? Response.Status.BAD_REQUEST
            : Response.Status.INTERNAL_SERVER_ERROR;

    return Response.status(responseStatus)
        .entity(
            ErrorModel.builder()
                .reason(exception.getErrorCode())
                .description(resourceBundle.getString(exception.getErrorCode().name()))
                .build())
        .build();
  }
}
