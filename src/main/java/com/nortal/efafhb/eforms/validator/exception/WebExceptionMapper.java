package com.nortal.efafhb.eforms.validator.exception;

import com.nortal.efafhb.eforms.validator.common.Constants;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Locale;
import java.util.ResourceBundle;

@Provider
public class WebExceptionMapper implements ExceptionMapper<WebApplicationException> {

  /**
   * Maps the provided WebApplicationException to a Response object.
   *
   * @param exception the WebApplicationException to be mapped
   * @return a Response object representing the mapped exception
   */
  @Override
  public Response toResponse(WebApplicationException exception) {
    ResourceBundle resourceBundle =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE));
    if (exception.getResponse().getStatus() == Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode()) {
      return Response.status(Status.UNSUPPORTED_MEDIA_TYPE)
          .entity(
              ErrorModel.builder()
                  .reason(ErrorCode.NO_VALID_CONTENT_TYPE)
                  .description(resourceBundle.getString(ErrorCode.NO_VALID_CONTENT_TYPE.name()))
                  .build())
          .build();
    } else if (exception.getResponse().getStatus() == Status.METHOD_NOT_ALLOWED.getStatusCode()) {
      return Response.status(Status.METHOD_NOT_ALLOWED)
          .entity(
              ErrorModel.builder()
                  .reason(ErrorCode.METHOD_NOT_ALLOWED)
                  .description(resourceBundle.getString(ErrorCode.METHOD_NOT_ALLOWED.name()))
                  .build())
          .build();
    } else {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(
              ErrorModel.builder()
                  .reason(ErrorCode.UNKNOWN_ERROR)
                  .description(resourceBundle.getString(ErrorCode.GENERIC_USER_NOTIFICATION.name()))
                  .build())
          .build();
    }
  }
}
