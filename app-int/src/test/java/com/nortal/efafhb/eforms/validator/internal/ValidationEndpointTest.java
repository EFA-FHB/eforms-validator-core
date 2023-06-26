package com.nortal.efafhb.eforms.validator.internal;

public interface ValidationEndpointTest {
  void test_validate_eforms();

  void test_validate_with_warnings_eforms();

  void test_validate_with_warnings_and_errors_eforms();

  void test_validate_with_errors_eforms();

  void test_validate_unsupported_media_type();

  void test_malformed_xml_exception();
}
