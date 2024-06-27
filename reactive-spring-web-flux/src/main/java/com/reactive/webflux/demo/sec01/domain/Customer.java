package com.reactive.webflux.demo.sec01.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "customer")
public class Customer {

    @Id
    private Long id;
    private String name;
    private String email;
}
