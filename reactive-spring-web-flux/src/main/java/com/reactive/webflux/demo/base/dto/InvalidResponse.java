package com.reactive.webflux.demo.base.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class InvalidResponse {

  private String errorCode;
  private int httpCode;
  private String message;
  private String title;
}
