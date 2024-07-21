package com.reactive.webflux.demo.sec03_route_f.config;

import com.reactive.webflux.demo.sec03_route_f.advice.ApplicationExceptionHandler;
import com.reactive.webflux.demo.sec03_route_f.dto.CustomerDto;
import com.reactive.webflux.demo.sec03_route_f.exceptions.CustomerNotFoundException;
import com.reactive.webflux.demo.sec03_route_f.exceptions.InvalidInputException;
import com.reactive.webflux.demo.sec03_route_f.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Configuration
public class RouteConfiguration {

  private final RequestHandler requestHandler;
  private final ApplicationExceptionHandler applicationExceptionHandler;

  public RouteConfiguration(
      RequestHandler requestHandler, ApplicationExceptionHandler applicationExceptionHandler) {
    this.requestHandler = requestHandler;
    this.applicationExceptionHandler = applicationExceptionHandler;
  }

  @Bean
  public RouterFunction<ServerResponse> userFunctions() {
    return RouterFunctions.route()
        .GET("/customers", req -> this.requestHandler.allCustomers(req))
        .GET("/customers/{id}", req -> this.requestHandler.getOneCustomer(req))
        .GET("/customers/paginated", req -> this.requestHandler.allCustomers(req))
        .POST("/customers", req -> this.requestHandler.saveCustomer(req))
        .PUT("/customers", req -> this.requestHandler.updateCustomer(req))
        .DELETE("/customers/{id}", req -> this.requestHandler.deleteCustomer(req))
        .onError(CustomerNotFoundException.class, this.applicationExceptionHandler::handleException)
        .onError(InvalidInputException.class, this.applicationExceptionHandler::handleException)
        // all another exceptions
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> route(CustomerService customerService) {
    return RouterFunctions.route()
        .GET(
            "/products",
            req ->
                customerService
                    .getAllCustomers()
                    .as(productFlux -> ServerResponse.ok().body(productFlux, CustomerDto.class)))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> route2(CustomerService customerService) {
    return RouterFunctions.route()
        .GET("/fluxes", req -> ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(getDelayedFlux(), String.class))
        .build();
  }

  private Flux<String> getDelayedFlux() {
    return Flux.just(
            "Element 1",
            "Element 2",
            "Element 3",
            "Element 4",
            "Element 5",
            "Element 6",
            "Element 7",
            "Element 38",
            "Element 39",
            "Element 10")
        .delayElements(Duration.ofSeconds(2))
            .doOnNext(System.out::println);
  }


  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);

    return new CorsWebFilter(source);
  }
}
