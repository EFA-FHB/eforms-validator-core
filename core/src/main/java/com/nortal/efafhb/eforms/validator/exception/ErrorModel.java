package com.nortal.efafhb.eforms.validator.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorModel {

  private ErrorCode reason;
  private String description;
}
