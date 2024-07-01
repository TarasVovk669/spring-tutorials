package com.reactive.webflux.demo.sec01;

import com.reactive.webflux.demo.sec01.domain.Customer;
import com.reactive.webflux.demo.sec01.dto.OrderDetails;
import com.reactive.webflux.demo.sec01.repository.CustomerOrderRepository;
import com.reactive.webflux.demo.sec01.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

public class Les01Test extends AbstractTest {

  private static final Logger log = LoggerFactory.getLogger(Les01Test.class);

  @Autowired private CustomerRepository customerRepository;
  @Autowired private CustomerOrderRepository customerOrderRepository;
  @Autowired private DatabaseClient databaseClient;

  @Test
  public void getAll() {
    this.customerRepository
        .findAll()
        .doOnNext(x -> log.info("{}", x))
        .as(StepVerifier::create)
        .expectNextCount(10)
        .verifyComplete();
  }

  @Test
  public void getOne() {
    this.customerRepository
        .findById(2L)
        .doOnNext(x -> log.info("{}", x))
        .as(StepVerifier::create)
        .assertNext(c -> Assertions.assertEquals("mike", c.getName()))
        .expectComplete()
        .verify();
  }

  @Test
  public void insertAndDeleteCustomer() {

    var customer = new Customer();
    customer.setEmail("example.@gmail.com");
    customer.setName("examole");

    customerRepository
        .save(customer)
        .doOnNext(x -> log.info("{}", x))
        .as(StepVerifier::create)
        .assertNext(c -> Assertions.assertEquals("examole", c.getName()))
        .expectComplete()
        .verify();

    customerRepository.count().as(StepVerifier::create).expectNext(11L).expectComplete().verify();

    customerRepository
        .deleteById(11L)
        .then(this.customerRepository.count())
        .as(StepVerifier::create)
        .expectNext(10L)
        .expectComplete()
        .verify();
  }

  @Test
  public void getAllPageable() {
    this.customerRepository
        .findBy(PageRequest.of(0, 3).withSort(Sort.by("name").ascending()))
        .doOnNext(x -> log.info("{}", x))
        .as(StepVerifier::create)
        .expectNextCount(3)
        .verifyComplete();
  }

  @Test
  public void getProductsByCustomer() {
    this.customerOrderRepository
        .getProductsOrderedByCustomer("mike")
        .doOnNext(x -> log.info("{}", x))
        .as(StepVerifier::create)
        .expectNextCount(2)
        .verifyComplete();
  }

  @Test
  public void getOrderDetails() {
    this.customerOrderRepository
        .getOrderDetails("iphone 20")
        .doOnNext(x -> log.info("{}", x))
        .as(StepVerifier::create)
        .assertNext(x -> Assertions.assertEquals(975, x.amount()))
        .assertNext(x -> Assertions.assertEquals(950, x.amount()))
        .verifyComplete();
  }

  @Test
  public void getOrderDetailsDbClient() {
    var query =
        """
        SELECT
                co.order_id,
                c.name AS customer_name,
                p.description AS product_name,
                co.amount,
                co.order_date
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON p.id = co.product_id
            WHERE
                p.description = :description
            ORDER BY co.amount DESC
        """;

    this.databaseClient
        .sql(query)
        .bind("description", "iphone 20")
        .mapProperties(OrderDetails.class)
        .all()
        .doOnNext(x -> log.info("{}", x))
        .as(StepVerifier::create)
        .assertNext(x -> Assertions.assertEquals(975, x.amount()))
        .assertNext(x -> Assertions.assertEquals(950, x.amount()))
        .verifyComplete();
  }
}
