package com.nortal.efafhb.eforms.validator.exception;

import lombok.Getter;

@Getter
public class ValidatorApplicationException extends RuntimeException {

  private final ErrorCode errorCode;
  private final transient Object[] messageArguments;

  /**
   * Constructs a new ValidatorApplicationException with the specified error code and message
   * arguments.
   *
   * @param errorCode the error code associated with the exception
   * @param messageArguments the message arguments used to format the error message
   */
  public ValidatorApplicationException(ErrorCode errorCode, Object... messageArguments) {
    this.errorCode = errorCode;
    this.messageArguments = messageArguments;
  }
}
