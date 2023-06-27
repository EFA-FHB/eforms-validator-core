package com.nortal.efafhb.eforms.validator.enums;

import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum EFormsSupportedVersion {
  V01("eforms-sdk-0.1", "0.1.1"),
  V10("eforms-sdk-1.0", "1.0.0"),
  V15("eforms-sdk-1.5", "1.5.1"),
  VD10("eforms-de-1.0", "1.0.1");

  private final String eformsVersion;
  private final String supportedVersion;

  EFormsSupportedVersion(String eformsVersion, String supportedVersion) {
    this.eformsVersion = eformsVersion;
    this.supportedVersion = supportedVersion;
  }

  public static String getSupportedVersion(String eformsVersion) {
    return Stream.of(values())
        .filter(v -> v.getEformsVersion().equals(eformsVersion))
        .findAny()
        .map(com.nortal.efafhb.eforms.validator.enums.EFormsSupportedVersion::getSupportedVersion)
        .orElse(null);
  }
}
