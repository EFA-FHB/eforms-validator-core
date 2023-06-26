package com.nortal.efafhb.eforms.validator.ext;

public interface ValidationEndpointTest {
  void test_validate_eforms_1_0();

  void test_validate_de_with_errors_eforms_1_0();

  void test_validate_with_errors_eforms_1_0();

  void test_validate_unsupported_media_type();

  void test_malformed_xml_exception();
}
