package com.nortal.efafhb.eforms.validator.ext;

import app.getxray.xray.junit.customjunitxml.annotations.XrayTest;
import com.nortal.efafhb.eforms.validator.ext.profiles.PhaxValidatorProfile;
import com.nortal.efafhb.eforms.validator.ext.rest.ValidationEndpoint;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import java.net.URL;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@QuarkusTest
@TestHTTPEndpoint(ValidationEndpoint.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestProfile(PhaxValidatorProfile.class)
class ValidationEndpointPhaxTest extends AbstractValidationEndpointTest
    implements ValidationEndpointTest {

  @TestHTTPResource("/v1/validation")
  private URL validationResourceEndpoint;

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-1066")
  public void test_validate_eforms_1_0() {
    validate(validationResourceEndpoint);
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-3865")
  public void test_validate_de_with_errors_eforms_1_0() {
    validateDEWithErrors(validationResourceEndpoint);
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = " D603345-3867")
  public void test_validate_with_errors_eforms_1_0() {
    validateWithErrors(validationResourceEndpoint);
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-3862")
  public void test_validate_unsupported_media_type() {
    validateUnsupportedMedia(validationResourceEndpoint);
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-3863")
  public void test_malformed_xml_exception() {
    test_malformed_xml_exception(validationResourceEndpoint);
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-3868")
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
