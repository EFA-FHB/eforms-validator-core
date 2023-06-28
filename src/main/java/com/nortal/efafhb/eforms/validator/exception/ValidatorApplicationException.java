package com.nortal.efafhb.eforms.validator.exception;

import lombok.Getter;

@Getter
public class ValidatorApplicationException extends RuntimeException {

  private final ErrorCode errorCode;
  private final transient Object[] messageArguments;

  public ValidatorApplicationException(ErrorCode errorCode, Object... messageArguments) {
    this.errorCode = errorCode;
    this.messageArguments = messageArguments;
  }
}
