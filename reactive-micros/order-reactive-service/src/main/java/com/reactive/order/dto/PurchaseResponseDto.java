package com.reactive.order.dto;

import lombok.Data;

@Data
public class PurchaseResponseDto {

    private Long orderId;
    private Long userId;
    private Long productId;
    private Long amount;
    private OrderStatus orderStatus;
}
