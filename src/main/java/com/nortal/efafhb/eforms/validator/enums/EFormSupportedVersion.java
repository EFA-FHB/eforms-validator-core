package com.nortal.efafhb.eforms.validator.enums;

import java.util.stream.Stream;
import lombok.Getter;

/** Enum representing the supported versions of eForms. */
@Getter
public enum EFormSupportedVersion {
  V01("eforms-sdk-0.1", "0.1.1"),
  V10("eforms-sdk-1.0", "1.0.0"),
  V15("eforms-sdk-1.5", "1.5.5"),
  V17("eforms-sdk-1.7", "1.7.2"),
  VD10("eforms-de-1.0", "1.0.1"),
  VD11("eforms-de-1.1", "1.1.0");

  private final String eformsVersion;
  private final String supportedVersion;

  /**
   * Constructs a new EFormSupportedVersion enum with the specified eForms version and supported
   * version.
   *
   * @param eformsVersion the eForms version
   * @param supportedVersion the supported version
   */
  EFormSupportedVersion(String eformsVersion, String supportedVersion) {
    this.eformsVersion = eformsVersion;
    this.supportedVersion = supportedVersion;
  }

  /**
   * Gets the supported version for the given eForms version.
   *
   * @param eformsVersion the eForms version
   * @return the supported version, or null if not found
   */
  public static String getSupportedVersion(String eformsVersion) {
    return Stream.of(values())
        .filter(v -> v.getEformsVersion().equals(eformsVersion))
        .findAny()
        .map(com.nortal.efafhb.eforms.validator.enums.EFormSupportedVersion::getSupportedVersion)
        .orElse(null);
  }
}
