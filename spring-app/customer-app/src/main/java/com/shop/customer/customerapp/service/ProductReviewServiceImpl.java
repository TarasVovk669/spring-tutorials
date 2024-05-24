package com.shop.customer.customerapp.service;

import com.shop.customer.customerapp.entity.ReviewProduct;
import com.shop.customer.customerapp.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;

    public Mono<ReviewProduct> createReview(Long prodId, int rating, String value) {
        return productReviewRepository.save(new ReviewProduct(UUID.randomUUID(), prodId, rating, value));
    }

    @Override
    public Flux<ReviewProduct> findAllForProduct(Long prodId) {
        return productReviewRepository.findAllByProductId(prodId);
    }
}
