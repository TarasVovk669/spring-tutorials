package com.aggregator.service.aggregatorservice.dto;


import com.aggregator.service.aggregatorservice.domain.Ticker;

public record Holding(Ticker ticker,
                      Integer quantity) {
}
