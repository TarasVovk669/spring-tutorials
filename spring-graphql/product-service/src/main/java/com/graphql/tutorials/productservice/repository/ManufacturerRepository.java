package com.graphql.tutorials.productservice.repository;

import com.graphql.tutorials.productservice.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>,
        JpaSpecificationExecutor<Manufacturer> {
}
