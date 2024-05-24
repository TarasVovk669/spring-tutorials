package com.shop.catalogue.catalogueservice.service;

import com.shop.catalogue.catalogueservice.domain.Product;
import com.shop.catalogue.catalogueservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Random RANDOM = new Random();

    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Product getProductCustomQ(Long id) {
        return productRepository.getProductCustom(id);
    }

    @Override
    public Product createProduct(String name, String description, BigDecimal price) {
        return productRepository.save(Product.builder()
                .price(price)
                .name(name)
                .description(description)
                .id(RANDOM.nextLong(0, 100))
                .build()
        );
    }

    @Override
    public void updateProduct(Long id, String name, String description, BigDecimal price) {
        productRepository.save(Product.builder()
                .price(price)
                .name(name)
                .description(description)
                .id(id)
                .build()
        );
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
