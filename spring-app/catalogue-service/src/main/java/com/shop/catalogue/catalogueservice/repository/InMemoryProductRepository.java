package com.shop.catalogue.catalogueservice.repository;

import com.shop.catalogue.catalogueservice.domain.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class InMemoryProductRepository implements ProductRepository {

    private static List<Product> products = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        products.add(Product.builder().id(12L).name("Bread").price(new BigDecimal("10.00")).description("This is bread").build());
        products.add(Product.builder().id(24L).name("Water").price(new BigDecimal("123.12")).description("This is water").build());
        products.add(Product.builder().id(76L).name("Eggs").price(new BigDecimal("9.567")).description("This is eggs").build());
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Product getProduct(Long id) {
        System.out.println("id: "+ id);
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElseThrow(() -> new NoSuchElementException("errors.product.not_found"));
    }

    @Override
    public Product createProduct(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        Optional.ofNullable(getProduct(product.getId()))
                .ifPresentOrElse(p -> {
                    p.setName(product.getName());
                    p.setDescription(product.getDescription());
                    p.setPrice(product.getPrice());
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }
}
