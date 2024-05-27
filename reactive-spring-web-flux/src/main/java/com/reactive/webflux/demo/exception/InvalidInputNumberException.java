package com.reactive.webflux.demo.exception;

import lombok.Data;

@Data
public class InvalidInputNumberException extends RuntimeException {

  private final String errorCode;
  private final Integer httpCode = 400;
  private final String title;
  private final String message;

  public InvalidInputNumberException(String errorCode, String title, String message) {
    this.errorCode = errorCode;
    this.title = title;
    this.message = message;
  }


}
