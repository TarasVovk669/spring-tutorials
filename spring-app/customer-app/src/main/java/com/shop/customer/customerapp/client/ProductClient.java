package com.shop.customer.customerapp.client;

import com.shop.customer.customerapp.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductClient {
    Flux<Product> findAllProducts();

    Mono<Product> getProduct(Long id);
}
