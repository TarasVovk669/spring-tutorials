package com.reactive.webflux.demo.sec03_route_f.config;

import com.reactive.webflux.demo.sec02.exceptions.ApplicationExceptions;
import com.reactive.webflux.demo.sec03_route_f.dto.CustomerDto;
import com.reactive.webflux.demo.sec03_route_f.service.CustomerService;
import com.reactive.webflux.demo.sec03_route_f.validator.RequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

  private final CustomerService customerService;

  public RequestHandler(CustomerService customerService) {
    this.customerService = customerService;
  }

  public Mono<ServerResponse> allCustomers(ServerRequest request) {
    Flux<CustomerDto> allCustomers = customerService.getAllCustomers();

    return allCustomers.as(flux -> ServerResponse.ok().body(flux, CustomerDto.class));
    // ServerResponse.ok()
    //         .body() //for mono or flux. For publisher type
    // .bodyValue() //use customer dto / use simple object
  }

  public Mono<ServerResponse> allCustomersPaginated(ServerRequest request) {
    Integer page = request.queryParam("page").map(Integer::parseInt).orElse(1);
    Integer size = request.queryParam("size").map(Integer::parseInt).orElse(10);
    Flux<CustomerDto> allCustomers = customerService.getAllCustomers(page, size);

    return allCustomers.as(flux -> ServerResponse.ok().body(flux, CustomerDto.class));
    // ServerResponse.ok()
    //         .body() //for mono or flux. For publisher type
    // .bodyValue() //use customer dto / use simple object
  }

  public Mono<ServerResponse> getOneCustomer(ServerRequest request) {
    var id = Integer.parseInt(request.pathVariable("id")); // get - like customers/{id}
    Mono<CustomerDto> oneCustomer = customerService.getCustomerById(id);

    return oneCustomer
        .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
        .flatMap(ServerResponse.ok()::bodyValue);
  }

  public Mono<ServerResponse> saveCustomer(ServerRequest request) {
    return request
        .bodyToMono(CustomerDto.class)
        .transform(RequestValidator.validate())
        .as(this.customerService::saveCustomer)
        .flatMap(ServerResponse.ok()::bodyValue);
  }

  public Mono<ServerResponse> updateCustomer(ServerRequest request) {
    var id = Integer.parseInt(request.pathVariable("id")); // get - like customers/{id}

    return request
        .bodyToMono(CustomerDto.class)
        .transform(RequestValidator.validate())
        .as(mono -> customerService.updateCustomer(id, mono))
        .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
        .flatMap(ServerResponse.ok()::bodyValue);
  }

  public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
    var id = Integer.parseInt(request.pathVariable("id")); // get - like customers/{id}

    return this.customerService
        .deleteCustomerById(id)
        .filter(b -> b) // if true we delete if false not delete
        .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
        .then(ServerResponse.ok().build());
  }
}
