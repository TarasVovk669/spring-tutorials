package com.aggregator.service.aggregatorservice.exceptions;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {

  public static <T> Mono<T> customerNotFound(Integer id) {
    return Mono.error(new CustomerNotFoundException(id));
  }

  public static <T> Mono<T> invalidTrade(String message) {
    return Mono.error(new InvalidTradeException(message));
  }

  public static <T> Mono<T> missingTicker() {
    return Mono.error(new InvalidTradeException("Missing ticker"));
  }

  public static <T> Mono<T> missingAction() {
    return Mono.error(new InvalidTradeException("Missing trade_action"));
  }

  public static <T> Mono<T> invalidQty() {
    return Mono.error(new InvalidTradeException("qty must be > 0"));
  }
}
