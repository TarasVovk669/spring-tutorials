package com.reactive.product.productreactiveservice.util;

import com.reactive.product.productreactiveservice.domain.Product;
import com.reactive.product.productreactiveservice.dto.ProductDto;

public class DtoUtil {

  public static ProductDto toDto(Product product) {
    return ProductDto.builder()
        .id(product.getId())
        .description(product.getDescription())
        .price(product.getPrice())
        .build();
  }

  public static Product toEntity(ProductDto dto) {
    return Product.builder()
        .id(dto.getId())
        .description(dto.getDescription())
        .price(dto.getPrice())
        .build();
  }
}
