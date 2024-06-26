package com.shop.catalogue.catalogueservice.repository;

import com.shop.catalogue.catalogueservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(name = "Product.getProductByIdCustom")
    Product getProductCustom(@Param("product_id") Long id);
}
