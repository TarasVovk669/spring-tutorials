package com.reactive.user.userreactiveservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long id;
    private String name;
    private Integer balance;
}
