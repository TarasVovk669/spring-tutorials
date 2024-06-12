package com.reactive.product.productreactiveservice.service;

import com.reactive.product.productreactiveservice.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<ProductDto> getAll();

    Flux<ProductDto> getAllByRange(Integer min, Integer max);

    Mono<ProductDto> getOne(String id);

    Mono<ProductDto> save(Mono<ProductDto> dto);

    Mono<ProductDto> update(String id, Mono<ProductDto> dto);

    Mono<Void> delete(String id);
}
