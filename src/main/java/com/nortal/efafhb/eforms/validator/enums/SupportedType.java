package com.nortal.efafhb.eforms.validator.enums;

import static com.nortal.efafhb.eforms.validator.validation.service.ValidatorUtil.EFORMS_SDK_VERSION_DELIMITER;

import com.nortal.efafhb.eforms.validator.exception.ErrorCode;
import com.nortal.efafhb.eforms.validator.exception.ValidatorApplicationException;
import java.util.Arrays;

public enum SupportedType {
  DE("eforms-de", "eforms-de"),
  EU("eforms-eu", "eforms-sdk");

  private final String value;
  private final String standardizedName;

  SupportedType(final String value, final String standardizedName) {
    this.value = value;
    this.standardizedName = standardizedName;
  }

  /**
   * Gets the value of the supported type.
   *
   * @return the value of the supported type
   */
  public String getValue() {
    return value;
  }

  /**
   * Gets the standardized name of the supported type.
   *
   * @return the standardized name of the supported type
   */
  public String getStandardizedName() {
    return standardizedName;
  }

  /**
   * Finds the SupportedType enum matching the provided value (case-insensitive).
   *
   * @param value the value to match
   * @return the SupportedType enum matching the provided value
   * @throws ValidatorApplicationException if no matching SupportedType is found
   */
  public static SupportedType findByValueIgnoreCase(String value) {
    return Arrays.stream(SupportedType.values())
        .filter(v -> v.getValue().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(() -> new ValidatorApplicationException(ErrorCode.INVALID_SDK_TYPE));
  }

  /**
   * Retrieves the SupportedType matching the provided eForms SDK version.
   *
   * @param sdkVersion the eForms version in the format used in the 'CustomizationID' element (e.g., 'eforms-sdk-X.X')
   * @return the SupportedType matching the provided eForms SDK version
   * @throws IllegalArgumentException if the provided sdkVersion is not supported
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
