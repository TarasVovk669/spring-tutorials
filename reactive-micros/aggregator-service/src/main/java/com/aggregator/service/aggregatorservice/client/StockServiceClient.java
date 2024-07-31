package com.aggregator.service.aggregatorservice.client;

import com.aggregator.service.aggregatorservice.domain.Ticker;
import com.aggregator.service.aggregatorservice.dto.PriceUpdate;
import com.aggregator.service.aggregatorservice.dto.StockPriceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;

public class StockServiceClient {

  private static Logger log = LoggerFactory.getLogger(StockServiceClient.class);

  private final WebClient webClient;
  private Flux<PriceUpdate> priceUpdateFlux;

  public StockServiceClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<StockPriceResponse> stock(Ticker ticker) {
    return this.webClient
        .get()
        .uri("/stock/{ticker}", ticker)
        .retrieve()
        .bodyToMono(StockPriceResponse.class);
  }

  public Flux<PriceUpdate> priceUpdateStream() {
    if (Objects.isNull(priceUpdateFlux)) {
      this.priceUpdateFlux = this.priceUpdate();
    }

    return this.priceUpdateFlux;
  }

  private Flux<PriceUpdate> priceUpdate() {
    return this.webClient
        .get()
        .uri("/stock/price-stream")
        .accept(MediaType.APPLICATION_NDJSON)
        .retrieve()
        .bodyToFlux(PriceUpdate.class)
        .retryWhen(retry())
        .cache(1); // for hot-publishers
  }

  private Retry retry() {
    return Retry.fixedDelay(100, Duration.ofSeconds(1))
        .doBeforeRetry(
            rs ->
                log.info(
                    "stock price stream call failed. retrying: {}", rs.failure().getMessage()));
  }
}
