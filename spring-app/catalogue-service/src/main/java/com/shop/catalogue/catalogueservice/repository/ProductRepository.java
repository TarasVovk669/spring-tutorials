package com.shop.catalogue.catalogueservice.repository;

import com.shop.catalogue.catalogueservice.domain.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getProducts();

    Product getProduct(Long id);

    Product createProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(Long id);
}
