package com.reactive.order.service;

import com.reactive.order.dto.PurchaseRequestDto;
import com.reactive.order.dto.PurchaseResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderFulfillmentService {
    Mono<PurchaseResponseDto> processDto(Mono<PurchaseRequestDto> dtoMono);

    Flux<PurchaseResponseDto> getPurchaseOrders(Long userId);
}
