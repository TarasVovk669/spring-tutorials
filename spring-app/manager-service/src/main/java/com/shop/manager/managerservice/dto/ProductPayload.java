package com.shop.manager.managerservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductPayload(Long id,
                             @NotNull(message = "{error.product.name.is_null}")
                             @Size(min = 1, max = 30, message = "{error.product.name.invalid_length}")
                             String name,

                             String description,
                             @NotNull BigDecimal price) {
}
