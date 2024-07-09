package com.reactive.webflux.demo.sec03_route_f.config;

import com.reactive.webflux.demo.sec03_route_f.advice.ApplicationExceptionHandler;
import com.reactive.webflux.demo.sec03_route_f.exceptions.CustomerNotFoundException;
import com.reactive.webflux.demo.sec03_route_f.exceptions.InvalidInputException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

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
        .GET("/paginated/customers", req -> this.requestHandler.allCustomers(req))
        .GET("/customers/{id}", req -> this.requestHandler.getOneCustomer(req))
        .POST("/customers", req -> this.requestHandler.saveCustomer(req))
        .PUT("/customers", req -> this.requestHandler.updateCustomer(req))
        .DELETE("/customers/{id}", req -> this.requestHandler.deleteCustomer(req))
        .onError(CustomerNotFoundException.class, this.applicationExceptionHandler::handleException)
        .onError(InvalidInputException.class, this.applicationExceptionHandler::handleException)
            //all another exceptions
        .build();
  }
}
