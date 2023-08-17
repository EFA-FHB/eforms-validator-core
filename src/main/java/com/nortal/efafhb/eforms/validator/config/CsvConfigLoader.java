package com.nortal.efafhb.eforms.validator.config;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.util.StringUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
public class CsvConfigLoader {
  private static final String DEFAULT_CONFIG_PATH = "/config/";

  private static final String COMMENT_LINE_PREFIX = "#";

  @Inject Instance<CsvConfig> configs;

  public void loadCsvConfigs(@Observes @Priority(1) StartupEvent event) {
    log.info("Loading csv configuration...");
    configs.stream().forEach(this::loadCsvData);
    log.info("Csv configuration loading completed!");
  }

  private void loadCsvData(CsvConfig config) {
    InputStream inputStream =
        openInputStream(config.getConfigFilepath(), config.getConfigFileName());

    try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        CSVReader reader = new CSVReader(inputStreamReader)) {
      String[] values;
      while ((values = reader.readNext()) != null) {
        if (!values[0].startsWith(COMMENT_LINE_PREFIX)) {
          config.loadConfigData(values);
        }
      }
    } catch (IOException | CsvValidationException e) {
      log.errorf(
          "Something went wrong reading csv file for '%s' config: %s",
          config.getClass().getSimpleName(), e.getMessage());
      throw new IllegalArgumentException("Invalid csv config file!");
    }
  }

  private InputStream openInputStream(String configFilepath, String configName) {
    try {
      if (StringUtil.isNullOrEmpty(configFilepath)) {
        return getClass().getResourceAsStream(DEFAULT_CONFIG_PATH + configName);
      } else {
        return new FileInputStream(configFilepath);
      }
    } catch (FileNotFoundException e) {
      log.errorf("Csv configuration file not found in path '%s'", configFilepath);
      throw new IllegalArgumentException("Csv configuration file not found: " + e);
    }
  }
}
