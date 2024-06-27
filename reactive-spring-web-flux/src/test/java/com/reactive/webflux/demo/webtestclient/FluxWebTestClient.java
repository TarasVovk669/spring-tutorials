package com.reactive.webflux.demo.webtestclient;

import com.reactive.webflux.demo.base.controller.ReactiveMathController;
import com.reactive.webflux.demo.base.dto.MathResponseDto;
import com.reactive.webflux.demo.base.dto.MultiplyRequestDto;
import com.reactive.webflux.demo.base.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@WebFluxTest(ReactiveMathController.class)
public class FluxWebTestClient {

  @Autowired private WebTestClient client;

  @MockBean private ReactiveMathService mathService;

  @Test
  public void fluentAssertationTest() {

    Mockito.when(mathService.findSquare(Mockito.anyInt()))
        .thenReturn(Mono.just(new MathResponseDto(25)));

    client
        .get()
        .uri("/reactive-math/square/{input}", 5)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(MathResponseDto.class)
        .value(r -> Assertions.assertThat(r.getResult()).isEqualTo(25));
  }

  @Test
  public void listResponseTest() {

    Flux<MathResponseDto> flux = Flux.range(1, 3).map(MathResponseDto::new);

    Mockito.when(mathService.listResult(Mockito.anyInt())).thenReturn(flux);

    client
        .get()
        .uri("/reactive-math/table/{input}", 5)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(MathResponseDto.class)
        .hasSize(3);
  }

  @Test
  public void streamingResponseTest() {

    Flux<MathResponseDto> flux =
        Flux.range(1, 3).map(MathResponseDto::new).delayElements(Duration.ofMillis(100));

    Mockito.when(mathService.listResult(Mockito.anyInt())).thenReturn(flux);

    client
        .get()
        .uri("/reactive-math/table/{input}/stream", 5)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectHeader()
        .contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
        .expectBodyList(MathResponseDto.class)
        .hasSize(3);
  }

  @Test
  public void postTest() {

    Mockito.when(mathService.multiply(Mockito.any())).thenReturn(Mono.just(new MathResponseDto(1)));

    client
        .post()
        .uri("/reactive-math/multiply")
        .headers(h -> h.setBasicAuth("user", "pass"))
        .headers(h -> h.set("example_header", "value123"))
        .bodyValue(MultiplyRequestDto.builder().one(1).two(2).build())
        .exchange()
        .expectStatus()
        .is2xxSuccessful();
  }

  @Test
  public void errorHandlingTest() {

    Mockito.when(mathService.multiply(Mockito.any())).thenReturn(Mono.just(new MathResponseDto(1)));

    client
        .get()
        .uri("/reactive-math/square/{input}/validated", 5)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.errorCode")
        .isEqualTo("ER001")
        .jsonPath("$.title")
        .isEqualTo("Invalid request")
        .jsonPath("$.message")
        .isEqualTo("number must be in range from 10 to 20");
  }
}
