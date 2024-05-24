package com.shop.customer.customerapp.service;

import com.shop.customer.customerapp.entity.ReviewProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewService {

    Mono<ReviewProduct> createReview(Long prodId, int rating, String value);

    Flux<ReviewProduct> findAllForProduct(Long prodId);
}
