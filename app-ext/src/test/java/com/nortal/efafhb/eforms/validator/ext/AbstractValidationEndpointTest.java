package com.nortal.efafhb.eforms.validator.ext;

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

  private final String INVALID_CONTENT_TYPE = MediaType.APPLICATION_JSON;
  private final String INVALID_SDK_TYPE = "eforms";
  private final String INVALID_EFORMS_VERSION = "10";
  private final String UNSUPPORTED_EFORMS_VERSION = "1.5";
  private static final String VALIDATION_ENTRY_XSD_TYPE = "XSD";
  private static final String VALIDATION_XSD_CODE = "XSD_VALIDATION_FAILED_CODE";
  private static final String VALIDATION_ENTRY_SCHEMATRON_TYPE = "SCHEMATRON";
  private static final String EFORMS_SDK_VERSION_PATTERN = "^eforms-de-[\\d+].[\\d+].[\\d+]$";

  protected ValidatableResponse validate(URL validationResourceEndpoint) {
    return given()
        .when()
        .multiPart("sdkType", "eforms-de")
        .multiPart("eformsVersion", "1.0")
        .multiPart("eforms", get10ContractNoticeValid())
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

  protected ValidatableResponse validateDEWithErrors(URL validationResourceEndpoint) {
    return given()
        .when()
        .multiPart("sdkType", "eforms-de")
        .multiPart("eformsVersion", "1.0")
        .multiPart("eforms", getDEInvalidContractNoticeWithErrors())
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
        .body("errors[0].path", notNullValue())
        .body("warnings", is(empty()))
        .body("validatedEformsVersion", notNullValue())
        .body("validatedEformsVersion", matchesRegex(Pattern.compile(EFORMS_SDK_VERSION_PATTERN)));
  }

  protected ValidatableResponse validateWithErrors(URL validationResourceEndpoint) {
    return given()
        .when()
        .multiPart("sdkType", "eforms-de")
        .multiPart("eformsVersion", "1.0")
        .multiPart("eforms", get10InvalidContractNoticeWithErrors())
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

  protected ValidatableResponse validateXSDWithErrors(URL validationResourceEndpoint) {
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(VALIDATION_XSD_CODE);
    return given()
        .when()
        .multiPart("sdkType", "eforms-de")
        .multiPart("eformsVersion", "1.0")
        .multiPart("eforms", get10ContractNoticeXSDInvalid())
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
        .body("reason", is(errorCode.name()))
        .body("description", is(description));
  }

  protected ValidatableResponse test_malformed_xml_exception(URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.MALFORMED_XML;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.MALFORMED_XML.name());

    return given()
        .when()
        .multiPart("sdkType", "eforms-de")
        .multiPart("eformsVersion", "1.0")
        .multiPart("eforms", get10InValidXml())
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body("reason", is(errorCode.name()))
        .body("description", is(description));
  }

  protected ValidatableResponse test_invalid_sdk_type_exception(URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.INVALID_SDK_TYPE;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.INVALID_SDK_TYPE.name());

    return given()
        .when()
        .multiPart("sdkType", INVALID_SDK_TYPE)
        .multiPart("eformsVersion", "1.0")
        .multiPart("eforms", get10ContractNoticeValid())
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
        .body("reason", is(errorCode.name()))
        .body("description", is(description));
  }

  protected ValidatableResponse test_invalid_eforms_version_exception(
      URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.BAD_REQUEST;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.BAD_EFORM_VERSION_FORMAT.name());

    return given()
        .when()
        .multiPart("sdkType", "eforms-de")
        .multiPart("eformsVersion", INVALID_EFORMS_VERSION)
        .multiPart("eforms", get10ContractNoticeValid())
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body("reason", is(errorCode.name()))
        .body("description", is(description));
  }

  protected ValidatableResponse test_unsupported_eforms_version_sdk_type_exception(
      URL validationResourceEndpoint) {
    ErrorCode errorCode = ErrorCode.UNSUPPORTED_EFORM_VERSION_SDK_TYPE;
    String description =
        ResourceBundle.getBundle(Constants.ERRORS, new Locale(Constants.LOCALE_DE))
            .getString(ErrorCode.UNSUPPORTED_EFORM_VERSION_SDK_TYPE.name());

    return given()
        .when()
        .multiPart("sdkType", "eforms-de")
        .multiPart("eformsVersion", UNSUPPORTED_EFORMS_VERSION)
        .multiPart("eforms", get10ContractNoticeValid())
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON)
        .post(validationResourceEndpoint)
        .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body("reason", is(errorCode.name()))
        .body("description", is(description));
  }

  private static File getDEInvalidContractNoticeWithErrors() {
    return getNoticeSample("notice_de_cn_10_error");
  }

  private static File get10InvalidContractNoticeWithErrors() {
    return getNoticeSample("notice_cn_10_invalid");
  }

  private static File get10ContractNoticeValid() {
    return getNoticeSample("notice_cn_10_valid");
  }

  private static File get10ContractNoticeXSDInvalid() {
    return getNoticeSample("notice_cn_10_invalid_xsd");
  }

  private static File get10InValidXml() {
    return getNoticeSample("malformed_xml");
  }

  private static File getNoticeSample(String fileName) {
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
