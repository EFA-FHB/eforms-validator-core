package com.nortal.efafhb.eforms.validator.exception;

import com.nortal.efafhb.eforms.validator.common.Constants;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Locale;
import java.util.ResourceBundle;
import lombok.extern.jbosslog.JBossLog;

/** Exception mapper for handling generic exceptions. */
@Provider
@JBossLog
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

  /**
   * Maps the provided exception to a Response object.
   *
   * @param exception the exception to be mapped
   * @return a Response object representing the mapped exception
   */
  @Override
  public Response toResponse(Exception exception) {
    ResourceBundle resourceBundle =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE));

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(
            ErrorModel.builder()
                .reason(ErrorCode.UNKNOWN_ERROR)
                .description(resourceBundle.getString(ErrorCode.GENERIC_USER_NOTIFICATION.name()))
                .build())
        .build();
  }
}
