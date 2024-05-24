package com.shop.customer.customerapp.repository;

import com.shop.customer.customerapp.entity.FavoriteProduct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Repository
public class FavoriteRepository {

    private final List<FavoriteProduct> favoriteProductsList = Collections.synchronizedList(new LinkedList<>());

    public Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct) {
        favoriteProductsList.add(favoriteProduct);
        return Mono.just(favoriteProduct);
    }

    public Mono<Void> deleteById(Long uuid) {
        favoriteProductsList.removeIf(p -> p.prodId().equals(uuid));
        return Mono.empty();
    }

    public Mono<FavoriteProduct> getById(Long uuid) {
        return Flux.fromIterable(favoriteProductsList)
                .filter(x -> x.prodId().equals(uuid))
                .singleOrEmpty();

    }
}
