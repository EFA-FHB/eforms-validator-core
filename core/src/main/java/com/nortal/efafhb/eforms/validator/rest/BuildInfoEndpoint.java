package com.nortal.efafhb.eforms.validator.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.InputStream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/info")
public class BuildInfoEndpoint {

  @GET
  @Produces(APPLICATION_JSON)
  public Response getBuildInfo() {
    try (InputStream inputStream = getClass().getResourceAsStream("/build_info.json")) {
      return Response.ok(inputStream.readAllBytes()).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
}
