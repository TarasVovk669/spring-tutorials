package com.reactive.webflux.demo.sec03_route_f.exceptions;

public class CustomerNotFoundException extends RuntimeException {

  public CustomerNotFoundException(Long id) {
    super("msg");
  }

  public CustomerNotFoundException(String message) {
    super(message);
  }
}
