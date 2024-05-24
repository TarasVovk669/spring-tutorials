package com.shop.customer.customerapp.client;

import com.shop.customer.customerapp.entity.Product;
import reactor.core.publisher.Flux;

public interface ProductClient {
    Flux<Product> findAllProducts();
}
