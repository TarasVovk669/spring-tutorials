package com.customer.service.dto;


import com.customer.service.domain.Ticker;

public record Holding(Ticker ticker,
                      Integer quantity) {
}
