package com.nortal.efafhb.eforms.validator.validation.output;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Builder
@Getter
public class ValidationEntry {
  private String rule;
  private String path;
  private String formattedMessage;
  @With private String description;
  private String test;
  @With private String type;
}
