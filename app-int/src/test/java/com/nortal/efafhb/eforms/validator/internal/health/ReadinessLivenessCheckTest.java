package com.nortal.efafhb.eforms.validator.internal.health;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import java.net.URL;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ReadinessLivenessCheckTest {

  @TestHTTPResource("/health/ready")
  private URL readinessEndpoint;

  @TestHTTPResource("/health/check")
  private URL livenessEndpoint;

  @Test
  void test_application_datasource_ready() {
    given()
        .when()
        .get(readinessEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("status", is("UP"));
  }

  @Test
  void test_application_liveness_ok() {
    given()
        .when()
        .get(livenessEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("status", is("UP"));
  }
}
