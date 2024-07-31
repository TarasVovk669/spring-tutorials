package com.aggregator.service.aggregatorservice.validator;

import com.aggregator.service.aggregatorservice.dto.TradeRequest;
import com.aggregator.service.aggregatorservice.exceptions.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

  public static UnaryOperator<Mono<TradeRequest>> validate() {
    return mono ->
        mono.filter(hasTicker())
            .switchIfEmpty(ApplicationExceptions.missingTicker())
            .filter(hasAction())
            .switchIfEmpty(ApplicationExceptions.missingAction())
            .filter(validQty())
            .switchIfEmpty(ApplicationExceptions.invalidQty());
  }

  private static Predicate<TradeRequest> hasTicker() {
    return dto -> dto.ticker() != null;
  }

  private static Predicate<TradeRequest> hasAction() {
    return dto -> dto.action() != null;
  }

  private static Predicate<TradeRequest> validQty() {
    return dto -> dto.quantity() != null && dto.quantity() > 0;
  }
}
