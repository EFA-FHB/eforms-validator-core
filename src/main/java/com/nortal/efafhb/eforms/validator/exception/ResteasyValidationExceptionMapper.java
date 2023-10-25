package com.nortal.efafhb.eforms.validator.exception;

import com.nortal.efafhb.eforms.validator.common.Constants;
import io.quarkus.hibernate.validator.runtime.jaxrs.ResteasyViolationExceptionImpl;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Locale;
import java.util.ResourceBundle;

/** Exception mapper for handling ResteasyViolationExceptionImpl. */
@Provider
public class ResteasyValidationExceptionMapper
    implements ExceptionMapper<ResteasyViolationExceptionImpl> {

  /**
   * Maps the provided ResteasyViolationExceptionImpl to a Response object.
   *
   * @param exception the ResteasyViolationExceptionImpl to be mapped
   * @return a Response object representing the mapped exception
   */
  @Override
  public Response toResponse(ResteasyViolationExceptionImpl exception) {
    ResourceBundle resourceBundle =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE));

    var violation = exception.getViolations().stream().findAny();
    String message =
        violation.isPresent() ? violation.get().getMessage() : ErrorCode.UNKNOWN_ERROR.name();
    return Response.status(Status.BAD_REQUEST)
        .entity(
            ErrorModel.builder()
                .reason(ErrorCode.BAD_REQUEST)
                .description(resourceBundle.getString(message))
                .build())
        .build();
  }
}
