package com.nortal.efafhb.eforms.validator.internal.rest;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BuildInfoResourceTest {

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
