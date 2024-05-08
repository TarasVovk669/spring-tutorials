package com.shop.manager.managerservice.client;


import com.shop.manager.managerservice.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CatalogueClient {

     List<Product> getAllProducts();

     Product create(String name, String description, BigDecimal price);

     Product getProductById(Long id);

     void update(Long id,String name, String description, BigDecimal price);

     void delete(Long id);
}
