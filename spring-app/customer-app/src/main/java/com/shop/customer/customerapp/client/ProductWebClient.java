package com.shop.customer.customerapp.client;

import com.shop.customer.customerapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

//@Component
@RequiredArgsConstructor
public class ProductWebClient implements ProductClient {

    private final WebClient webClient;

    @Override
    public Flux<Product> findAllProducts() {
        return this.webClient.get()
                .uri("/catalogue-api/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
