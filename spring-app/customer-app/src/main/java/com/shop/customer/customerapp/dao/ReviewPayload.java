package com.shop.customer.customerapp.dao;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewPayload(@NotNull
                            @Min(1) @Max(5) Integer rating, @NotNull @NotBlank String value) {
}
