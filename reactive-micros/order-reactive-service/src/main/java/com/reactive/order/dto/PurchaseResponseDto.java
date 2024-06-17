package com.reactive.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseResponseDto {

    private Long orderId;
    private Long userId;
    private String productId;
    private Long amount;
    private OrderStatus orderStatus;
}
