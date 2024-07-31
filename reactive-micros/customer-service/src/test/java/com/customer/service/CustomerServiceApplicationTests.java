package com.customer.service;

import com.customer.service.domain.Ticker;
import com.customer.service.domain.TradeAction;
import com.customer.service.dto.StockTradeRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@SpringBootTest
@AutoConfigureWebTestClient
class CustomerServiceApplicationTests {

  private static Logger log = LoggerFactory.getLogger(CustomerServiceApplicationTests.class);

  @Autowired private WebTestClient client;

  @Test
  void customerInformation_Success() {
    getCustomer(1L, HttpStatus.OK)
        .jsonPath("$.name")
        .isEqualTo("Sam")
        .jsonPath("$.balance")
        .isEqualTo("10000")
        .jsonPath("$.holdings")
        .isEmpty();
  }

  @Test
  void customerInformation_NotFound() {
    getCustomer(100L, HttpStatus.NOT_FOUND)
        .jsonPath("$.detail")
        .isEqualTo("Customer [id=100] not found");

    var sell = new StockTradeRequest(Ticker.GOOGLE, 100, 5, TradeAction.SELL);
    getTrade(20L, sell, HttpStatus.NOT_FOUND)
        .jsonPath("$.detail")
        .isEqualTo("Customer [id=20] not found");
  }

  @Test
  void buyAndSellStock() {
    var buy = new StockTradeRequest(Ticker.GOOGLE, 100, 5, TradeAction.BUY);
    getTrade(2L, buy, HttpStatus.OK).jsonPath("$.balance").isEqualTo(9500);

    // check the holdings
    getCustomer(2L, HttpStatus.OK)
        .jsonPath("$.holdings")
        .isNotEmpty()
        .jsonPath("$.holdings.length()")
        .isEqualTo(1)
        .jsonPath("$.holdings[0].ticker")
        .isEqualTo("GOOGLE")
        .jsonPath("$.holdings[0].quantity")
        .isEqualTo(5);

    var sell = new StockTradeRequest(Ticker.GOOGLE, 100, 5, TradeAction.SELL);
    getTrade(2L, sell, HttpStatus.OK).jsonPath("$.balance").isEqualTo(10000);

    // check the holdings
    getCustomer(2L, HttpStatus.OK)
        .jsonPath("$.holdings")
        .isNotEmpty()
        .jsonPath("$.holdings.length()")
        .isEqualTo(1)
        .jsonPath("$.holdings[0].ticker")
        .isEqualTo("GOOGLE")
        .jsonPath("$.holdings[0].quantity")
        .isEqualTo(0);
  }

  private WebTestClient.BodyContentSpec getCustomer(Long customerId, HttpStatus status) {
    return client
        .get()
        .uri("/customers/{customerId}", customerId)
        .exchange()
        .expectStatus()
        .isEqualTo(status)
        .expectBody()
        .consumeWith(x -> log.info("{}", new String(Objects.requireNonNull(x.getResponseBody()))));
  }

  private WebTestClient.BodyContentSpec getTrade(
      Long customerId, StockTradeRequest request, HttpStatus status) {
    return client
        .post()
        .uri("/customers/{customerId}/trade", customerId)
        .bodyValue(request)
        .exchange()
        .expectStatus()
        .isEqualTo(status)
        .expectBody()
        .consumeWith(x -> log.info("{}", new String(Objects.requireNonNull(x.getResponseBody()))));
  }
}
