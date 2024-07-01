package com.reactive.webflux.demo.sec01.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "product")
public class Product {

    @Id
    private Long id;
    private String description;
    private Integer price;
}
