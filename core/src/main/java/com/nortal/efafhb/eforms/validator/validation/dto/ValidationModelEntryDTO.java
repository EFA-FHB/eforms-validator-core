package com.nortal.efafhb.eforms.validator.validation.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationModelEntryDTO implements Serializable {

  private String type;
  private String description;
  private String rule;
  private String ruleContent;
  private String path;
}
