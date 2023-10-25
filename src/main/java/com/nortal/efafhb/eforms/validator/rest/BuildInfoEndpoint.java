package com.nortal.efafhb.eforms.validator.rest;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.io.InputStream;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/** REST endpoint for retrieving build information. */
@Path("/info")
public class BuildInfoEndpoint {

  /**
   * Retrieves the build information.
   *
   * @return a Response object containing the build information
   * @throws IOException if there is an error reading the build information
   */
  @GET
  @Produces(APPLICATION_JSON)
  public Response getBuildInfo() throws IOException {
    InputStream inputStream = getClass().getResourceAsStream("/build_info.json");
    assert inputStream != null;
    return Response.ok(inputStream.readAllBytes()).build();
  }
}
