package com.reactive.webflux.demo.sec01.repository;

import com.reactive.webflux.demo.sec01.domain.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer,Long> {

    Flux<Customer> findBy(Pageable pageable);
}
