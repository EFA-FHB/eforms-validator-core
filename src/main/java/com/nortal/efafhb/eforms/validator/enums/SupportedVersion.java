package com.nortal.efafhb.eforms.validator.enums;

import java.util.Arrays;
import java.util.Map;
import lombok.Getter;

@Getter
public enum SupportedVersion {
  V1_5_5("1.5.5"),
  V1_7_0("1.7.0"),
  V1_0_1("1.0.1"),
  V1_0_0("1.0.0"),
  V0_1_1("0.1.1"),
  V1_1_0("1.1.0");

  private final String value;

  private static final Map<SupportedVersion, SupportedVersion> sdkVersionsForGdk =
      Map.of(
          SupportedVersion.V1_0_1, SupportedVersion.V1_5_5,
          SupportedVersion.V1_1_0, SupportedVersion.V1_7_0);

  SupportedVersion(String value) {
    this.value = value;
  }

  /**
   * EForms SDK version fallows pattern eforms-sdk-X.X. Version numbers from string (X.X) is
   * extracted and compared with loaded validator versions. If version is not supported by validator
   * exception will be thrown
   *
   * @param sdkVersion eforms sdk version string (pattern eforms-sdk-X.X.)
   * @return enum of supported version {@link IllegalArgumentException} will be thrown if version
   *     from input is not recognized
   */
  public static SupportedVersion versionFromSDK(String sdkVersion) {
    var suportedVersion = EFormSupportedVersion.getSupportedVersion(sdkVersion);
    return Arrays.stream(SupportedVersion.values())
        .filter(v -> v.getValue().equals(suportedVersion))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("Not supported version of sdk: %s", suportedVersion)));
  }

  /**
   * Retrieves the map of combinations of eForms gdk and corresponding SDK versions.
   *
   * @return the map of combinations of eForms gdk and corresponding SDK versions
   */
  public static Map<SupportedVersion, SupportedVersion> sdkVersionsForGdk() {
    return sdkVersionsForGdk;
  }
}
