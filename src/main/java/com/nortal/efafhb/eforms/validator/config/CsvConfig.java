package com.nortal.efafhb.eforms.validator.config;

/** Configuration to be loaded during runtime from csv file */
public interface CsvConfig {

  /**
   * Method used for loading csv configuration data and mapping it to any internal structure, for
   * later retrieval
   *
   * @param data configuration values, it is up for implementation to interpret them accordingly
   */
  void loadConfigData(String[] data);

  /**
   * Get filename (including the suffix) of an original csv configuration file which should reside
   * within resources/config folder. If {@link #getConfigFilepath()} returns null this configuration
   * file will be used as default config.
   *
   * @return filename of default csv configuration file.
   */
  String getConfigFileName();

  /**
   * Get path of csv configuration file. If set to null, then default configuration will be used.
   *
   * @return full filepath of csv configuration file.
   */
  default String getConfigFilepath() {
    return null;
  }
}
