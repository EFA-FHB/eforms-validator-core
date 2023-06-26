package com.nortal.efafhb.eforms.validator.internal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import com.nortal.efafhb.eforms.validator.internal.rest.BusinessDocumentValidationEndpoint;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@QuarkusTest
@TestHTTPEndpoint(BusinessDocumentValidationEndpoint.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BusinessDocumentValidationEndpointTest {

  @TestHTTPResource("/v1/validation-business-document")
  private URL businessDocumentValidationResourceEndpoint;

  @Test
  void test_validate_business_document() {
    given()
        .when()
        .multiPart("business_document", getBusinessDocumentSample("business_document"))
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(businessDocumentValidationResourceEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("valid", is(false))
        .body("errors", not(empty()))
        .body("errors[0].type", notNullValue());
  }

  private File getBusinessDocumentSample(String fileName) {
    try {
      return new File(
          BusinessDocumentValidationEndpointTest.class
              .getClassLoader()
              .getResource("/peppol/%s.xml".formatted(fileName))
              .toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
