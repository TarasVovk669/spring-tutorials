package com.shop.customer.customerapp.client;

import com.shop.customer.customerapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class ProductWebClient implements ProductClient {

    private final WebClient webClient;

    @Override
    public Flux<Product> findAllProducts() {
        return this.webClient
                .get()
                .uri("/catalogue-api/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }

    @Override
    public Mono<Product> getProduct(Long id) {
        return this.webClient
                .get()
                .uri("/catalogue-api/products/{product_id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .onErrorComplete(WebClientResponseException.BadRequest.class);
    }
}
