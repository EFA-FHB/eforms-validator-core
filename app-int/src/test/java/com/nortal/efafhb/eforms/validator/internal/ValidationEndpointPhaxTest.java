package com.nortal.efafhb.eforms.validator.internal;

import app.getxray.xray.junit.customjunitxml.annotations.XrayTest;
import com.nortal.efafhb.eforms.validator.internal.profiles.PhaxValidatorProfile;
import com.nortal.efafhb.eforms.validator.internal.rest.ValidationEndpoint;
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
  @XrayTest(key = "D603345-3889")
  public void test_validate_eforms() {
    validate(
        validationResourceEndpoint,
        EU_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_1_0,
        getNoticeSample("notice_cn_10_valid"));
    validate(
        validationResourceEndpoint,
        EU_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_1_5,
        getNoticeSample("notice_cn_15_valid"));
    validate(
        validationResourceEndpoint,
        EU_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_0_1,
        getNoticeSample("notice_valid_eu_cn_01"));
    validate(
        validationResourceEndpoint,
        DE_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_1_0,
        getNoticeSample("notice_cn_de_10_valid"));
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-3890")
  public void test_validate_with_warnings_eforms() {
    validateWithWarnings(
        validationResourceEndpoint,
        EU_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_1_0,
        getNoticeSample("notice_cn_10_warning"));
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-3891")
  public void test_validate_with_warnings_and_errors_eforms() {
    validateWithWarningsAndErrors(
        validationResourceEndpoint,
        EU_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_1_0,
        getNoticeSample("notice_cn_10_warning_and_error"));
    validateWithWarningsAndErrors(
        validationResourceEndpoint,
        EU_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_1_5,
        getNoticeSample("notice_cn_15_warning_and_error"));
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-3892")
  public void test_validate_with_errors_eforms() {
    validateWithErrors(
        validationResourceEndpoint,
        EU_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_1_0,
        getNoticeSample("notice_cn_10_invalid"));
    validateWithErrors(
        validationResourceEndpoint,
        EU_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_1_5,
        getNoticeSample("notice_cn_15_invalid"));
    validateWithErrors(
        validationResourceEndpoint,
        EU_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_0_1,
        getNoticeSample("cn_24_maximal_error"));
    validateWithErrors(
        validationResourceEndpoint,
        DE_SDK_TYPE,
        SUPPORTED_EFORMS_VERSION_1_0,
        getNoticeSample("notice_cn_de_10_invalid"));
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-3886")
  public void test_validate_unsupported_media_type() {
    validateUnsupportedMedia(validationResourceEndpoint);
  }

  @Test
  @Tag(TestSuite.XRAY_TEST)
  @XrayTest(key = "D603345-3887")
  public void test_malformed_xml_exception() {
    test_malformed_xml_exception(validationResourceEndpoint);
  }

  @Test
  public void test_invalid_sdk_type_exception() {
    test_invalid_sdk_type_exception(validationResourceEndpoint);
  }

  @Test
  public void test_invalid_eforms_version_exception() {
    test_invalid_eforms_version_exception(validationResourceEndpoint);
  }

  @Test
  public void test_unsupported_eforms_version_sdk_type_exception() {
    test_unsupported_eforms_version_sdk_type_exception(validationResourceEndpoint);
  }

  @Test
  void test_eforms_invalid_xsd() {
    validateXSDWithErrors(
        validationResourceEndpoint,
        SUPPORTED_EFORMS_VERSION_1_0,
        getNoticeSample("notice_cn_10_invalid_xsd"));
    validateXSDWithErrors(
        validationResourceEndpoint,
        SUPPORTED_EFORMS_VERSION_1_5,
        getNoticeSample("notice_cn_15_invalid_xsd"));
    validateXSDWithErrors(
        validationResourceEndpoint,
        SUPPORTED_EFORMS_VERSION_0_1,
        getNoticeSample("notice_invalid"));
  }

  @Test
  void test_disabled_validations() {
    test_disabled_validations(validationResourceEndpoint);
  }

  @Test
  void test_only_schematron_validation_disabled() {
    test_schematron_validation_disabled(validationResourceEndpoint);
  }

  @Test
  void test_only_xsd_validation_disabled() {
    test_xsd_validation_disabled(validationResourceEndpoint);
  }
}
