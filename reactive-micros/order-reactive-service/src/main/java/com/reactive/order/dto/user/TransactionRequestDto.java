package com.reactive.order.dto.user;

import lombok.Data;

@Data
public class TransactionRequestDto {

    private Long userId;
    private Integer amount;

}
