package com.nortal.efafhb.eforms.validator.ext.profiles;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Map;

public class PhaxValidatorProfile implements QuarkusTestProfile {

  @Override
  public Map<String, String> getConfigOverrides() {

    return Map.of(
        "eforms-validator.engine",
        "phax",
        "eforms-validator.api.supported_eforms_versions",
        "eforms-de-1.0,eforms-sdk-1.5");
  }
}
