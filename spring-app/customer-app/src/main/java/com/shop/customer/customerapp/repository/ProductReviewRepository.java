package com.shop.customer.customerapp.repository;

import com.shop.customer.customerapp.entity.FavoriteProduct;
import com.shop.customer.customerapp.entity.ReviewProduct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProductReviewRepository {

    private final List<ReviewProduct> reviewProducts = Collections.synchronizedList(new LinkedList<>());

    public Mono<ReviewProduct> save(ReviewProduct reviewProduct) {
        reviewProducts.add(reviewProduct);
        return Mono.just(reviewProduct);
    }

    public Flux<ReviewProduct> findAllByProductId(Long prodId) {
        return Flux.fromIterable(reviewProducts)
                .filter(p -> p.prodId().equals(prodId));
    }

}
