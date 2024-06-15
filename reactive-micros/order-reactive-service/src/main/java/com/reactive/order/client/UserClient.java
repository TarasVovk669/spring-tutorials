package com.reactive.order.client;

import com.reactive.order.dto.product.ProductDto;
import com.reactive.order.dto.user.TransactionRequestDto;
import com.reactive.order.dto.user.TransactionResponseDto;
import com.reactive.order.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

  private final WebClient webClient;

  public UserClient(@Value("${services.user-service.url}") String url) {
    this.webClient = WebClient.builder().baseUrl(url).build();
  }

  public Mono<UserDto> getUserById(Long userId) {
    return webClient.get().uri("{id}", userId).retrieve().bodyToMono(UserDto.class);
  }

  public Mono<TransactionResponseDto> authorizeTrx(TransactionRequestDto requestDto) {
    return this.webClient
        .post()
        .uri("/transactions")
        .bodyValue(requestDto)
        .retrieve()
        .bodyToMono(TransactionResponseDto.class);
  }
}
