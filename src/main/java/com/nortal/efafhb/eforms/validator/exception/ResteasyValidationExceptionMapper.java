package com.nortal.efafhb.eforms.validator.exception;

import com.nortal.efafhb.eforms.validator.common.Constants;
import io.quarkus.hibernate.validator.runtime.jaxrs.ResteasyViolationExceptionImpl;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResteasyValidationExceptionMapper
    implements ExceptionMapper<ResteasyViolationExceptionImpl> {

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
