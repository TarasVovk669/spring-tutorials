package com.reactive.order.dto;

import com.reactive.order.dto.product.ProductDto;
import com.reactive.order.dto.user.TransactionRequestDto;
import com.reactive.order.dto.user.TransactionResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestContext {

    private PurchaseRequestDto purchaseRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

}
