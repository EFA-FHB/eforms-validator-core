package com.nortal.efafhb.eforms.validator.validation;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import java.util.List;
import javax.validation.constraints.Pattern;

@ConfigMapping(prefix = "eforms-validator.api")
public interface ValidationConfig {

  String EFORMS_VERSION_REGEXP = "^eforms-(sdk|de)-[\\d+].[\\d+]$";
  String EFORMS_VERSION_MESSAGE = "Bad eForm version format";

  /**
   * Retrieves the list of supported eForms versions.
   *
   * @return the list of supported eForms versions
   */
  @WithName("supported_eforms_versions")
  List<@Pattern(regexp = EFORMS_VERSION_REGEXP, message = EFORMS_VERSION_MESSAGE) String>
      supportedEFormsVersions();
}
