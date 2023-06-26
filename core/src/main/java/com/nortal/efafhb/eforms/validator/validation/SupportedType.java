package com.nortal.efafhb.eforms.validator.validation;

import static com.nortal.efafhb.eforms.validator.validation.ValidatorUtil.EFORMS_SDK_VERSION_DELIMITER;

import com.nortal.efafhb.eforms.validator.exception.ErrorCode;
import com.nortal.efafhb.eforms.validator.exception.ValidatorApplicationException;
import java.util.Arrays;

enum SupportedType {
  DE("eforms-de", "eforms-de"),
  EU("eforms-eu", "eforms-sdk");

  private final String value;
  private final String standardizedName;

  SupportedType(final String value, final String standardizedName) {
    this.value = value;
    this.standardizedName = standardizedName;
  }

  public String getValue() {
    return value;
  }

  public String getStandardizedName() {
    return standardizedName;
  }

  public static SupportedType findByValueIgnoreCase(String value) {
    return Arrays.stream(SupportedType.values())
        .filter(v -> v.getValue().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(() -> new ValidatorApplicationException(ErrorCode.INVALID_SDK_TYPE));
  }

  /**
   * @param sdkVersion - eForms version in format used in 'CustomizationID' element
   * @return the {@link SupportedType} matching the provided sdkVersion given in format
   *     'eforms-[sg]dk-X.X'
   */
  public static SupportedType typeFromSDK(String sdkVersion) {
    String standardizedSdkType =
        sdkVersion.substring(0, sdkVersion.lastIndexOf(EFORMS_SDK_VERSION_DELIMITER));
    return Arrays.stream(SupportedType.values())
        .filter(v -> v.getStandardizedName().equalsIgnoreCase(standardizedSdkType))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("Not supported type of sdk: %s", sdkVersion)));
  }
}
