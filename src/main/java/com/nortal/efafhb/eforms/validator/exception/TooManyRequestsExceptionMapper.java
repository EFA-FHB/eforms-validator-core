package com.nortal.efafhb.eforms.validator.exception;

import com.nortal.efafhb.eforms.validator.common.Constants;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/** Exception mapper for handling TooManyRequestsException. */
@Provider
public class TooManyRequestsExceptionMapper implements ExceptionMapper<TooManyRequestsException> {

  /**
   * Maps the provided TooManyRequestsException to a Response object.
   *
   * @param exception the TooManyRequestsException to be mapped
   * @return a Response object representing the mapped exception
   */
  @Override
  public Response toResponse(TooManyRequestsException exception) {
    ResourceBundle resourceBundle =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE));
    return Response.status(Status.TOO_MANY_REQUESTS)
        .entity(
            ErrorModel.builder()
                .reason(ErrorCode.TOO_MANY_REQUESTS)
                .description(resourceBundle.getString(ErrorCode.TOO_MANY_REQUESTS.name()))
                .build())
        .build();
  }
}
