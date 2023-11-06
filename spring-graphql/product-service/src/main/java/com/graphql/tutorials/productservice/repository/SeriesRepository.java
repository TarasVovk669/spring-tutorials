package com.graphql.tutorials.productservice.repository;

import com.graphql.tutorials.productservice.domain.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SeriesRepository extends JpaRepository<Series, Long>,
        JpaSpecificationExecutor<Series> {
}
