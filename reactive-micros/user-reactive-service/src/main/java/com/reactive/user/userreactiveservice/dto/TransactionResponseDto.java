package com.reactive.user.userreactiveservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponseDto {

    private Long id;
    private Long userId;
    private Integer amount;
    private TransactionStatus status;
}
