package com.reactive.webflux.demo.webtestclient;

import com.reactive.webflux.demo.configuration.RouteConfig;
import com.reactive.webflux.demo.configuration.RouteHandler;
import com.reactive.webflux.demo.controller.ReactiveMathController;
import com.reactive.webflux.demo.dto.MathResponseDto;
import com.reactive.webflux.demo.dto.MultiplyRequestDto;
import com.reactive.webflux.demo.service.ReactiveMathService;
import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {RouteConfig.class})
public class RouterFunctionTestClient {

  @Autowired private WebTestClient client;
  // @Autowired private RouteConfig config;
  @Autowired private ApplicationContext ctx;
  @MockBean private RouteHandler routeHandler;

  @BeforeAll
  public void init() {
    // WebTestClient.bindToRouterFunction(config.highLevelRouter()).build();
    WebTestClient.bindToApplicationContext(ctx).build();
  }

  @Test
  public void postTest() {
    Mockito.when(routeHandler.squareHandler(Mockito.any()))
        .thenReturn(ServerResponse.ok().bodyValue(new MathResponseDto(1)));

    client
        .get()
        .uri("/api/route/square/{input}", 5)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(MathResponseDto.class)
        .value(r -> Assertions.assertThat(r.getResult()).isEqualTo(1));
  }
}
