package com.reactive.webflux.demo.configuration;

import com.reactive.webflux.demo.dto.InvalidResponse;
import com.reactive.webflux.demo.exception.InvalidInputNumberException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

  private final RouteHandler handler;

  @Bean
  public RouterFunction<ServerResponse> highLevelRouter() {
    return RouterFunctions.route().path("api", this::routerFunction).build();
  }

  private RouterFunction<ServerResponse> routerFunction() {
    return RouterFunctions.route()
        .GET("/route/square/{input}", handler::squareHandler)
        .GET("/route/square-pattern/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), handler::squareHandler)
        .GET("/route/table/{input}", handler::tableHandler)
        .GET("/route/table/{input}/validated", handler::tableHandlerWithValidation)
        .GET("/route/table/{input}/stream", handler::tableStreamHandler)
        .POST("/route/multiply", handler::multiplyHandler)
        .onError(InvalidInputNumberException.class, exceptionHandler())
        .build();
  }

  private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
    return (throwable, request) -> {
      var ex = (InvalidInputNumberException) throwable;

      InvalidResponse response =
          InvalidResponse.builder()
              .errorCode(ex.getErrorCode())
              .title(ex.getTitle())
              .message(ex.getMessage())
              .httpCode(ex.getHttpCode())
              .build();

      return ServerResponse.badRequest().bodyValue(response);
    };
  }
}
