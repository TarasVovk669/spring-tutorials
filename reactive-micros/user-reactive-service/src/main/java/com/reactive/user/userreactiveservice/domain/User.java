package com.reactive.user.userreactiveservice.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "t_user")
public class User {

    @Id
    private Long id;

    private String name;

    private Integer balance;
}
