package com.reactive.webflux.demo.sec03_route_f.advice;


import java.net.URI;
import java.util.function.Consumer;

import com.reactive.webflux.demo.sec03_route_f.exceptions.CustomerNotFoundException;
import com.reactive.webflux.demo.sec03_route_f.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class ApplicationExceptionHandler {

  public Mono<ServerResponse> handleException(CustomerNotFoundException ex, ServerRequest request) {
    return handleException(
        HttpStatus.NOT_FOUND,
        ex,
        request,
        problemDetail -> {
          problemDetail.setType(URI.create("http://example.com/problems/customer-not-found"));
          problemDetail.setTitle("Customer Not Found");
        });
  }

  public Mono<ServerResponse> handleException(InvalidInputException ex, ServerRequest request) {
    return handleException(
        HttpStatus.BAD_REQUEST,
        ex,
        request,
        problemDetail -> {
          problemDetail.setType(URI.create("http://example.com/problems/invalid-input"));
          problemDetail.setTitle("Invalid Input");
        });
  }

  public Mono<ServerResponse> handleException(
      HttpStatus status, Exception ex, ServerRequest request, Consumer<ProblemDetail> consumer) {
    var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
    consumer.accept(problem);
    problem.setInstance(URI.create(request.path()));
    return ServerResponse.status(status.value()).bodyValue(problem);
  }

}
