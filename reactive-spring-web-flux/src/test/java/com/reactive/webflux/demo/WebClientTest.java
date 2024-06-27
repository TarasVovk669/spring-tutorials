package com.reactive.webflux.demo;

import static org.junit.jupiter.api.Assertions.*;

import com.reactive.webflux.demo.base.dto.MathResponseDto;
import com.reactive.webflux.demo.base.dto.MultiplyRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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

  @Test
  public void fluxTableTest() {
    var mathResponseDtoMono =
        webClient
            .get()
            .uri("/reactive-math/table/{input}/stream", 5)
            .retrieve()
            .bodyToFlux(MathResponseDto.class);

    StepVerifier.create(mathResponseDtoMono).expectNextCount(10).verifyComplete();
  }

  @Test
  public void monoPostRequestMultiplyTest() {
    var mathResponseDto =
        webClient
            .post()
            .uri("/reactive-math/multiply")
            .headers(h -> h.set("some-key", "some-val"))
            .bodyValue(MultiplyRequestDto.builder().one(5).two(10).build())
            .retrieve()
            .bodyToMono(MathResponseDto.class);

    StepVerifier.create(mathResponseDto)
        .expectNextMatches(resp -> resp.getResult().equals(50))
        .verifyComplete();
  }

  @Test
  public void monoBadRequestTest() {
    var mathResponseDto =
        webClient
            .get()
            .uri("/reactive-math/square/{input}/validated", 5)
            .retrieve()
            .bodyToMono(MathResponseDto.class);

    StepVerifier.create(mathResponseDto).verifyError(WebClientResponseException.BadRequest.class);
  }

  // @Test
  public void monoQueryParamTest() {
    /*URI uri =
    UriComponentsBuilder.fromUriString(
            "http://localhost:8080/example?count={count}&page={page}")
        .build(10, 20);*/

    webClient
        .get()
        // .uri(uri)
        .uri(b -> b.path("example").query("count={count}&page={page}").build(10, 20))
        .retrieve()
        .bodyToMono(MathResponseDto.class);
  }

  @Test
  public void monoAttributeTest() {
    var mathResponseDtoMono =
        webClient
            .get()
            .uri("/reactive-math/square/{input}", 5)
            .attribute("auth", "basic")
            .retrieve()
            .bodyToMono(MathResponseDto.class);

    StepVerifier.create(mathResponseDtoMono).expectNextCount(1).verifyComplete();
  }
}
