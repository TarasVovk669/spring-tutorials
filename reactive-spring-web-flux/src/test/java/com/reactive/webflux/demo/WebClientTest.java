package com.reactive.webflux.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.reactive.webflux.demo.dto.MathResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class WebClientTest extends BaseTest {

  @Autowired private WebClient webClient;

  @Test
  public void blockMonoTest() {
    MathResponseDto mathResponseDtoMono =
        webClient
            .get()
            .uri("/reactive-math/square/{input}", 5)
            .retrieve()
            .bodyToMono(MathResponseDto.class)
            .block();

    assertNotNull(mathResponseDtoMono);
    assertEquals(25, mathResponseDtoMono.getResult());
  }

  @Test
  public void nonBlockMonoTestStepVerifier() {
    var mathResponseDtoMono =
        webClient
            .get()
            .uri("/reactive-math/square/{input}", 5)
            .retrieve()
            .bodyToMono(MathResponseDto.class);

    StepVerifier.create(mathResponseDtoMono)
        .expectNextMatches(r -> r.getResult() == 25)
        .verifyComplete();

  }
}
