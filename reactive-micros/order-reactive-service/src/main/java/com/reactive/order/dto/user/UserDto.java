package com.reactive.order.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long id;
    private String name;
    private Integer balance;
}
