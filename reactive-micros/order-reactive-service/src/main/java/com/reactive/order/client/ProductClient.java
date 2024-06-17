package com.reactive.order.client;

import com.reactive.order.dto.product.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

  private final WebClient webClient;

  public ProductClient(@Value("${services.product-service.url}") String url) {
    this.webClient = WebClient.builder().baseUrl(url).build();
  }

  public Mono<ProductDto> getProductById(String productId) {
    return webClient.get().uri("/{productId}", productId).retrieve().bodyToMono(ProductDto.class);
  }
}
