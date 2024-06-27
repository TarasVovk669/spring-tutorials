package com.reactive.webflux.demo.sec01.repository;

import com.reactive.webflux.demo.sec01.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer,Long> {}
