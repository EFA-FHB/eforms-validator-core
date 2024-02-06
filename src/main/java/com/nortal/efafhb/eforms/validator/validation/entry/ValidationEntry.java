package com.nortal.efafhb.eforms.validator.validation.entry;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

@Builder
@Getter
@ToString
public class ValidationEntry {
  private String rule;
  private String path;
  private String formattedMessage;
  @With private String description;
  private String test;
  @With private String type;
}
