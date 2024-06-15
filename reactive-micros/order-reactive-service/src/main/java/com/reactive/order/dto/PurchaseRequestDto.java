package com.reactive.order.dto;

import lombok.Data;

@Data
public class PurchaseRequestDto {

    private Long userId;
    private Long productId;
}
