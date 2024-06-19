package com.reactive.webflux.demo.webtestclient;

import com.reactive.webflux.demo.dto.MathResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest  // create a lot of instances
@AutoConfigureWebTestClient
public class WebTestClientLib {

  @Autowired private WebTestClient client;

  @Test
  public void nonBlockMonoTestStepVerifier() {
    var mathResponseDtoMono =
        client
            .get()
            .uri("/reactive-math/square/{input}", 5)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .returnResult(MathResponseDto.class)
            .getResponseBody();

    StepVerifier.create(mathResponseDtoMono)
        .expectNextMatches(r -> r.getResult() == 25)
        .verifyComplete();
  }

  @Test
  public void fluentAssertationTest() {
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
}
