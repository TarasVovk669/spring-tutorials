package com.reactive.order.controller;

import com.reactive.order.dto.PurchaseRequestDto;
import com.reactive.order.dto.PurchaseResponseDto;
import com.reactive.order.service.OrderFulfillmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseOrderController {

  private final OrderFulfillmentService orderFulfillmentService;

  @PostMapping
  public Mono<ResponseEntity<PurchaseResponseDto>> process(
      @RequestBody Mono<PurchaseRequestDto> purchaseRequestDtoMono) {
    return orderFulfillmentService
        .processDto(purchaseRequestDtoMono)
        .map(ResponseEntity::ok)
        .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
        .onErrorReturn(
            WebClientRequestException.class,
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
  }

  @GetMapping
  public Flux<PurchaseResponseDto> purchaseResponseDtoFlux(@RequestParam("userId") Long userId) {
    return orderFulfillmentService.getPurchaseOrders(userId);
  }
}
