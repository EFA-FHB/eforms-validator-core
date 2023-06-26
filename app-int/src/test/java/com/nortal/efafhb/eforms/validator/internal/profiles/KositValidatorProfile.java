package com.nortal.efafhb.eforms.validator.internal.profiles;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Map;

public class KositValidatorProfile implements QuarkusTestProfile {

  @Override
  public Map<String, String> getConfigOverrides() {

    return Map.of(
        "eforms-validator.engine",
        "kosit",
        "eforms-validator.api.supported_eforms_versions",
        "eforms-sdk-0.1,eforms-sdk-1.0,eforms-sdk-1.5,eforms-de-1.0");
  }
}
