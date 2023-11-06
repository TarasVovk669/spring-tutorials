package com.graphql.tutorials.productservice.repository;

import com.graphql.tutorials.productservice.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ModelRepository extends JpaRepository<Model, Long>,
        JpaSpecificationExecutor<Model> {


}
