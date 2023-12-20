package com.nortal.efafhb.eforms.validator.validation.rest;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BuildInfoEndpointTest {

  @Test
  void getBuildInfo_fileExists_infoReturned() {
    given()
        .when()
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .get("/info")
        .then()
        .statusCode(Status.OK.getStatusCode())
        .body("name", notNullValue())
        .body("build", notNullValue());
  }
}
