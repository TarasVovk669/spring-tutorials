package com.reactive.product.productreactiveservice.repository;

import com.reactive.product.productreactiveservice.domain.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    //Flux<Product> getAllByPriceBetween(Integer min, Integer max);
    Flux<Product> getAllByPriceBetween(Range<Integer> range);
}
