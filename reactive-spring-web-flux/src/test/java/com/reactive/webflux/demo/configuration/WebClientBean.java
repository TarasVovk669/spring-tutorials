package com.reactive.webflux.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientBean {

  @Bean
  public WebClient webClient() {
    return WebClient.builder().baseUrl("http://localhost:8080").filter(this::sessionToken).build();
  }

  /*private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
    System.out.println("Generate session token");
    ClientRequest clientRequest =
        ClientRequest.from(request).headers(h -> h.setBearerAuth("Some_string_value_jwt")).build();
    return ex.exchange(clientRequest);
  }*/

  private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
    System.out.println("Generate session token");

    ClientRequest clientRequest = request.attribute("auth")
            .map(v -> v.equals("basic") ? withBasic(request) : withOAuth(request))
            .orElse(request);
    return ex.exchange(clientRequest);
  }

  private ClientRequest withOAuth(ClientRequest request){
    return ClientRequest.from(request)
            .headers(h-> h.setBearerAuth("some_value_jwt"))
            .build();
  }

  private ClientRequest withBasic(ClientRequest request){
    return ClientRequest.from(request)
            .headers(h-> h.setBasicAuth("login","password"))
            .build();
  }
}
