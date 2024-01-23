package com.virtualthread.config;

import com.virtualthread.external.api.ExternalApiOne;
import com.virtualthread.external.api.ExternalApiSecond;
import java.net.http.HttpClient;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientsConfig {

  @Value("${spring.threads.virtual.enabled}")
  private boolean isVirtualThreadEnabled;

  @Bean
  ExternalApiOne externalApiOne() {
    var clientBuilder = RestClient.builder().baseUrl("http://localhost:7070/sec02");

    if (isVirtualThreadEnabled) {
      clientBuilder.requestFactory(
              new JdkClientHttpRequestFactory(
                      HttpClient.newBuilder()
                              .executor(Executors.newVirtualThreadPerTaskExecutor())
                              .build()));
    }
    var client = clientBuilder.build();
    HttpServiceProxyFactory factory =
            HttpServiceProxyFactory.builderFor(RestClientAdapter.create(client)).build();
    return factory.createClient(ExternalApiOne.class);
  }

  @Bean
  ExternalApiSecond externalApiSecond() {
    var clientBuilder = RestClient.builder().baseUrl("http://localhost:7070/sec03");

    if (isVirtualThreadEnabled) {
      clientBuilder.requestFactory(
          new JdkClientHttpRequestFactory(
              HttpClient.newBuilder()
                  .executor(Executors.newVirtualThreadPerTaskExecutor())
                  .build()));
    }
    var client = clientBuilder.build();

    HttpServiceProxyFactory factory =
        HttpServiceProxyFactory.builderFor(RestClientAdapter.create(client)).build();
    return factory.createClient(ExternalApiSecond.class);
  }
}
