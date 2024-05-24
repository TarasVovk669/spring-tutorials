package com.shop.customer.customerapp.service;

import com.shop.customer.customerapp.entity.FavoriteProduct;
import com.shop.customer.customerapp.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    public Mono<FavoriteProduct> addProductToFavorite(Long prodId) {
        return favoriteRepository.save(new FavoriteProduct(UUID.randomUUID(), prodId));
    }

    @Override
    public Mono<Void> removeProductFromFavorite(Long prodId) {
        return favoriteRepository.deleteById(prodId);
    }

    @Override
    public Mono<FavoriteProduct> findById(Long id) {
        return favoriteRepository.getById(id);
    }
}
