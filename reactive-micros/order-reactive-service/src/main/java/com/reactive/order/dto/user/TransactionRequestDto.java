package com.reactive.order.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequestDto {

    private Long userId;
    private Integer amount;

}
