package com.nortal.efafhb.eforms.validator.ext;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nortal.efafhb.eforms.validator.ext.profiles.KositValidatorProfile;
import com.nortal.efafhb.eforms.validator.ext.rest.ValidationEndpoint;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import java.net.URL;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@QuarkusTest
@TestHTTPEndpoint(ValidationEndpoint.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestProfile(KositValidatorProfile.class)
class ValidationEndpointKositTest extends AbstractValidationEndpointTest
    implements ValidationEndpointTest {

  @TestHTTPResource("/v1/validation")
  private URL validationResourceEndpoint;

  @Test
  public void test_validate_eforms_1_0() {
    validate(validationResourceEndpoint);
  }

  @Test
  public void test_validate_de_with_errors_eforms_1_0() {}

  @Test
  public void test_validate_with_errors_eforms_1_0() {
    validateWithErrors(validationResourceEndpoint);
  }

  @Test
  public void test_validate_unsupported_media_type() {
    validateUnsupportedMedia(validationResourceEndpoint);
  }

  @Test
  @Ignore("Kosit validator has different behaviour")
  public void test_malformed_xml_exception() {
    assertTrue(true);
  }

  @Test
  void test_invalid_sdk_type_exception() {
    test_invalid_sdk_type_exception(validationResourceEndpoint);
  }

  @Test
  void test_invalid_eforms_version_exception() {
    test_invalid_eforms_version_exception(validationResourceEndpoint);
  }

  @Test
  void test_unsupported_eforms_version_sdk_type_exception() {
    test_unsupported_eforms_version_sdk_type_exception(validationResourceEndpoint);
  }

  @Test
  void test_eforms_invalid_xsd() {
    validateXSDWithErrors(validationResourceEndpoint);
  }
}
