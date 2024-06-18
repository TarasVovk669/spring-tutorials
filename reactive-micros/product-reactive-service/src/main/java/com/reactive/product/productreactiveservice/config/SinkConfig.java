package com.reactive.product.productreactiveservice.config;

import com.reactive.product.productreactiveservice.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

  @Bean
  public Sinks.Many<ProductDto> productDtoMany() {
    return Sinks.many().replay().limit(1);
  }

  @Bean
  public Flux<ProductDto> flux(Sinks.Many<ProductDto> sink) {
    return sink.asFlux();
  }
}
