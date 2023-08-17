package com.nortal.efafhb.eforms.validator.validation.profiles;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.util.Map;

public class PhaxValidatorIgnoredRulesProfile implements QuarkusTestProfile {

  @Override
  public Map<String, String> getConfigOverrides() {

    return Map.of(
        "eforms-validator.engine",
        "phax",
        "eforms-validator.api.supported_eforms_versions",
        "eforms-sdk-0.1,eforms-sdk-1.0,eforms-sdk-1.5,eforms-sdk-1.7,eforms-de-1.1",
        "ignored_rules.config.filepath",
        "src/test/resources/config/ignored_rules_test.csv");
  }
}
