package com.aggregator.service.aggregatorservice;

import com.aggregator.service.aggregatorservice.service.CustomerPortfolioService;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

public class CustomerInformationTest extends AggregatorServiceApplicationTests {

  private static final Logger log = LoggerFactory.getLogger(CustomerInformationTest.class);

  @Test
  public void customerInformation() {
    mockCI(1, "customer-service/success-response.json", 200);

    getCustomerInformation(1, HttpStatus.OK)
        .jsonPath("$.id")
        .isEqualTo(1)
        .jsonPath("$.name")
        .isEqualTo("Sam")
        .jsonPath("$.balance")
        .isEqualTo(10000);
  }

  @Test
  public void customerInformationNotFound() {
    mockCI(1, "customer-service/404.json", 404);

    getCustomerInformation(1, HttpStatus.NOT_FOUND)
        .jsonPath("$.detail")
        .isEqualTo("Customer [id=1] not found");
  }

  private void mockCI(Integer id, String path, int respCode) {
    var response = this.resourceToString(path);

    mockServerClient
        .when(HttpRequest.request("/customers/" + id))
        .respond(
            HttpResponse.response(response)
                .withStatusCode(respCode)
                .withContentType(MediaType.APPLICATION_JSON));
  }

  private WebTestClient.BodyContentSpec getCustomerInformation(
      Integer customerId, HttpStatus expectedStatus) {
    return this.webTestClient
        .get()
        .uri("/customers/{customerId}", customerId)
        .exchange()
        .expectStatus()
        .isEqualTo(expectedStatus)
        .expectBody()
        .consumeWith(c -> log.info("{}", new String(Objects.requireNonNull(c.getResponseBody()))));
  }
}
