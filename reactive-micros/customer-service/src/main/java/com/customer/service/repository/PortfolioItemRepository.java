package com.customer.service.repository;

import com.customer.service.domain.Customer;
import com.customer.service.domain.PortfolioItem;
import com.customer.service.domain.Ticker;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PortfolioItemRepository extends ReactiveCrudRepository<PortfolioItem, Integer> {

    Flux<PortfolioItem> findAllByCustomerId(Integer customerId);

    Mono<PortfolioItem> findAllByCustomerIdAndTicker(Integer customerId, Ticker ticker);
}
