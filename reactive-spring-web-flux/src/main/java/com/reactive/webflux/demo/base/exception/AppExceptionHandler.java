package com.reactive.webflux.demo.base.exception;

import com.reactive.webflux.demo.base.dto.InvalidResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(InvalidInputNumberException.class)
  public ResponseEntity<InvalidResponse> handleInvalidInputValidationException(
      InvalidInputNumberException e) {
    var invalidResponse =
        InvalidResponse.builder()
            .errorCode(e.getErrorCode())
            .title(e.getTitle())
            .message(e.getMessage())
            .httpCode(e.getHttpCode())
            .build();

    return ResponseEntity.badRequest().body(invalidResponse);
  }
}
