package com.shop.catalogue.catalogueservice.service;

import com.shop.catalogue.catalogueservice.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    Product getProduct(Long id);
    Product getProductCustomQ(Long id);

    Product createProduct(String name, String description, BigDecimal price);

    void updateProduct(Long id, String name, String description, BigDecimal price);

    void deleteProduct(Long id);
}
