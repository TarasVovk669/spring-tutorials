package com.shop.customer.customerapp.service;

import com.shop.customer.customerapp.entity.FavoriteProduct;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FavoriteService {

    Mono<FavoriteProduct> addProductToFavorite(Long prodId);

    Mono<Void> removeProductFromFavorite(Long prodId);

    Mono<FavoriteProduct> findById(Long id);


}
