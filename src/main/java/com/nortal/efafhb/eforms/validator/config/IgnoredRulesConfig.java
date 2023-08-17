package com.nortal.efafhb.eforms.validator.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Singleton;
import lombok.Builder;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
public class IgnoredRulesConfig implements CsvConfig {

  private static final String CONFIG_FILE_NAME = "ignored_rules.csv";

  @ConfigProperty(name = "ignored_rules.config.filepath")
  Optional<String> configFilepath;

  private final Map<String, List<String>> ignoredRules = new HashMap<>();

  @Override
  public void loadConfigData(String[] data) {
    IgnoredRule ignoredRule = IgnoredRule.builder().sdkVersion(data[0]).ruleId(data[1]).build();

    if (!ignoredRules.containsKey(ignoredRule.getSdkVersion())) {
      ignoredRules.put(ignoredRule.getSdkVersion(), new ArrayList<>());
    }

    ignoredRules.get(ignoredRule.getSdkVersion()).add(ignoredRule.getRuleId());
  }

  @Override
  public String getConfigFileName() {
    return CONFIG_FILE_NAME;
  }

  @Override
  public String getConfigFilepath() {
    return configFilepath.orElse(null);
  }

  public List<String> getIgnoredRules(String sdkVersion) {
    return Optional.ofNullable(ignoredRules.get(sdkVersion)).orElseGet(ArrayList::new);
  }

  @Builder
  @Getter
  public static class IgnoredRule {

    private String sdkVersion; // i.e. eforms-sdk-1.0.0
    private String ruleId;
  }
}
