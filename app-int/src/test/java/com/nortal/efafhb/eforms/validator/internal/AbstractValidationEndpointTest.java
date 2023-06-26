package com.nortal.efafhb.eforms.validator.internal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesRegex;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.nortal.efafhb.eforms.validator.common.Constants;
import com.nortal.efafhb.eforms.validator.exception.ErrorCode;
import io.restassured.response.ValidatableResponse;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

abstract class AbstractValidationEndpointTest {

  private static final String SDK_TYPE = "sdkType";
  private static final String EFORMS_VERSION = "eformsVersion";
  private static final String EFORMS = "eforms";
  private static final String XSD_VALIDATION = "xsdValidation";
  private static final String SCHEMATRON_VALIDATION = "schematronValidation";
  private static final String REASON = "reason";
  private static final String DESCRIPTION = "description";
  private final String INVALID_CONTENT_TYPE = MediaType.APPLICATION_JSON;
  private final String INVALID_SDK_TYPE = "eforms";
  private final String INVALID_EFORMS_VERSION = "10";
  private final String UNSUPPORTED_EFORMS_VERSION = "0.7";
  static final String EU_SDK_TYPE = "eforms-EU";
  static final String DE_SDK_TYPE = "eforms-DE";
  static final String SUPPORTED_EFORMS_VERSION_1_0 = "1.0";
  static final String SUPPORTED_EFORMS_VERSION_1_5 = "1.5";
  static final String SUPPORTED_EFORMS_VERSION_0_1 = "0.1";

  private static final String VALIDATION_ENTRY_SCHEMATRON_TYPE = "SCHEMATRON";
  private static final String VALIDATION_ENTRY_XSD_TYPE = "XSD";
  private static final String VALIDATION_XSD_CODE = "XSD_VALIDATION_FAILED_CODE";
  private static final String EFORMS_SDK_VERSION_PATTERN = "^eforms-(sdk|de)-[\\d+].[\\d+].[\\d+]$";

  protected ValidatableResponse validate(
      URL validationResourceEndpoint, String sdkType, String eFormsVersion, File notice) {
    return given()
        .when()
        .multiPart(SDK_TYPE, sdkType)
        .multiPart(EFORMS_VERSION, eFormsVersion)
        .multiPart(EFORMS, notice)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("errors", empty())
        .body("warnings", empty())
        .body("valid", is(true))
        .body("validatedEformsVersion", notNullValue())
        .body("validatedEformsVersion", matchesRegex(Pattern.compile(EFORMS_SDK_VERSION_PATTERN)));
  }

  protected ValidatableResponse validateWithWarnings(
      URL validationResourceEndpoint, String sdkType, String eFormsVersion, File notice) {
    return given()
        .when()
        .multiPart(SDK_TYPE, sdkType)
        .multiPart(EFORMS_VERSION, eFormsVersion)
        .multiPart(EFORMS, notice)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("valid", is(true))
        .body("errors", empty())
        .body("warnings", not(empty()))
        .body("warnings[0].type", notNullValue())
        .body("warnings[0].type", is(VALIDATION_ENTRY_SCHEMATRON_TYPE))
        .body("warnings[0].description", notNullValue())
        .body("warnings[0].rule", notNullValue())
        .body("warnings[0].ruleContent", notNullValue())
        .body("warnings[0].path", notNullValue())
        .body("validatedEformsVersion", notNullValue())
        .body("validatedEformsVersion", matchesRegex(Pattern.compile(EFORMS_SDK_VERSION_PATTERN)));
  }

  protected ValidatableResponse validateWithWarningsAndErrors(
      URL validationResourceEndpoint, String sdkType, String eFormsVersion, File notice) {
    return given()
        .when()
        .multiPart(SDK_TYPE, sdkType)
        .multiPart(EFORMS_VERSION, eFormsVersion)
        .multiPart(EFORMS, notice)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("valid", is(false))
        .body("errors", not(empty()))
        .body("errors[0].type", notNullValue())
        .body(
            "errors[0].type",
            either(is(VALIDATION_ENTRY_SCHEMATRON_TYPE)).or(is(VALIDATION_ENTRY_XSD_TYPE)))
        .body("errors[0].rule", notNullValue())
        .body("errors[0].ruleContent", notNullValue())
        .body("errors[0].path", notNullValue())
        .body("warnings", not(empty()))
        .body("warnings[0].type", notNullValue())
        .body("warnings[0].type", is(VALIDATION_ENTRY_SCHEMATRON_TYPE))
        .body("warnings[0].rule", notNullValue())
        .body("warnings[0].ruleContent", notNullValue())
        .body("warnings[0].path", notNullValue())
        .body("validatedEformsVersion", notNullValue())
        .body("validatedEformsVersion", matchesRegex(Pattern.compile(EFORMS_SDK_VERSION_PATTERN)));
  }

  protected ValidatableResponse validateWithErrors(
      URL validationResourceEndpoint, String sdkType, String eFormsVersion, File notice) {
    return given()
        .when()
        .multiPart(SDK_TYPE, sdkType)
        .multiPart(EFORMS_VERSION, eFormsVersion)
        .multiPart(EFORMS, notice)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("valid", is(false))
        .body("errors", not(empty()))
        .body("errors[0].type", notNullValue())
        .body(
            "errors[0].type",
            either(is(VALIDATION_ENTRY_SCHEMATRON_TYPE)).or(is(VALIDATION_ENTRY_XSD_TYPE)))
        .body("errors[0].description", notNullValue())
        .body("validatedEformsVersion", notNullValue())
        .body("validatedEformsVersion", matchesRegex(Pattern.compile(EFORMS_SDK_VERSION_PATTERN)));
  }

  protected ValidatableResponse validateXSDWithErrors(
      URL validationResourceEndpoint, String eFormsVersion, File notice) {
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(VALIDATION_XSD_CODE);
    return given()
        .when()
        .multiPart(SDK_TYPE, EU_SDK_TYPE)
        .multiPart(EFORMS_VERSION, eFormsVersion)
        .multiPart(EFORMS, notice)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("errors", not(empty()))
        .body("errors", hasSize(1))
        .body("errors[0].type", is(VALIDATION_ENTRY_XSD_TYPE))
        .body("errors[0].description", is(description))
        .body("errors[0].rule", nullValue())
        .body("errors[0].ruleContent", nullValue())
        .body("errors[0].path", nullValue())
        .body("warnings", empty())
        .body("valid", is(false))
        .body("validatedEformsVersion", notNullValue())
        .body("validatedEformsVersion", matchesRegex(Pattern.compile(EFORMS_SDK_VERSION_PATTERN)));
  }

  protected ValidatableResponse validateUnsupportedMedia(URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.NO_VALID_CONTENT_TYPE;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.NO_VALID_CONTENT_TYPE.name());

    return given()
        .when()
        .contentType(INVALID_CONTENT_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode())
        .body(REASON, is(errorCode.name()))
        .body(DESCRIPTION, is(description));
  }

  protected ValidatableResponse test_malformed_xml_exception(URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.MALFORMED_XML;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.MALFORMED_XML.name());

    return given()
        .when()
        .multiPart(SDK_TYPE, EU_SDK_TYPE)
        .multiPart(EFORMS_VERSION, "1.0")
        .multiPart(EFORMS, getNoticeSample("malformed_xml"))
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body(REASON, is(errorCode.name()))
        .body(DESCRIPTION, is(description));
  }

  protected ValidatableResponse test_invalid_sdk_type_exception(URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.BAD_REQUEST;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.INVALID_SDK_TYPE.name());

    return given()
        .when()
        .multiPart(SDK_TYPE, INVALID_SDK_TYPE)
        .multiPart(EFORMS_VERSION, "1.0")
        .multiPart(EFORMS, getNoticeSample("notice_cn_10_valid"))
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body(REASON, is(errorCode.name()))
        .body(DESCRIPTION, is(description));
  }

  protected ValidatableResponse test_invalid_eforms_version_exception(
      URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.BAD_REQUEST;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.BAD_EFORM_VERSION_FORMAT.name());

    return given()
        .when()
        .multiPart(SDK_TYPE, EU_SDK_TYPE)
        .multiPart(EFORMS_VERSION, INVALID_EFORMS_VERSION)
        .multiPart(EFORMS, getNoticeSample("notice_cn_10_valid"))
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body(REASON, is(errorCode.name()))
        .body(DESCRIPTION, is(description));
  }

  protected ValidatableResponse test_unsupported_eforms_version_sdk_type_exception(
      URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.UNSUPPORTED_EFORM_VERSION_SDK_TYPE;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.UNSUPPORTED_EFORM_VERSION_SDK_TYPE.name());

    return given()
        .when()
        .multiPart(SDK_TYPE, EU_SDK_TYPE)
        .multiPart(EFORMS_VERSION, UNSUPPORTED_EFORMS_VERSION)
        .multiPart(EFORMS, getNoticeSample("notice_cn_10_valid"))
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body(REASON, is(errorCode.name()))
        .body(DESCRIPTION, is(description));
  }

  protected ValidatableResponse test_disabled_validations(URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.VALIDATION_DISABLED;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.VALIDATION_DISABLED.name());
    return given()
        .when()
        .multiPart(SDK_TYPE, EU_SDK_TYPE)
        .multiPart(EFORMS_VERSION, SUPPORTED_EFORMS_VERSION_1_0)
        .multiPart(EFORMS, getNoticeSample("notice_cn_10_valid"))
        .multiPart(XSD_VALIDATION, false)
        .multiPart(SCHEMATRON_VALIDATION, false)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body(REASON, is(errorCode.name()))
        .body(DESCRIPTION, is(description));
  }

  protected ValidatableResponse test_schematron_validation_disabled(
      URL validationResourceEndpoint) {
    return given()
        .when()
        .multiPart(SDK_TYPE, EU_SDK_TYPE)
        .multiPart(EFORMS_VERSION, SUPPORTED_EFORMS_VERSION_1_0)
        .multiPart(EFORMS, getNoticeSample("notice_cn_10_valid"))
        .multiPart(XSD_VALIDATION, true)
        .multiPart(SCHEMATRON_VALIDATION, false)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("errors", empty())
        .body("warnings", empty())
        .body("valid", is(true))
        .body("validatedEformsVersion", notNullValue())
        .body("validatedEformsVersion", matchesRegex(Pattern.compile(EFORMS_SDK_VERSION_PATTERN)));
  }

  protected ValidatableResponse test_xsd_validation_disabled(URL validationResourceEndpoint) {
    return given()
        .when()
        .multiPart(SDK_TYPE, EU_SDK_TYPE)
        .multiPart(EFORMS_VERSION, SUPPORTED_EFORMS_VERSION_1_0)
        .multiPart(EFORMS, getNoticeSample("notice_cn_10_warning_and_error"))
        .multiPart(XSD_VALIDATION, false)
        .multiPart(SCHEMATRON_VALIDATION, true)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("errors", not(empty()))
        .body("warnings", not(empty()))
        .body("valid", is(false));
  }

  static File getNoticeSample(String fileName) {
    try {
      return new File(
          AbstractValidationEndpointTest.class
              .getClassLoader()
              .getResource("/eforms/%s.xml".formatted(fileName))
              .toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
