package com.aggregator.service.aggregatorservice.dto;

import com.aggregator.service.aggregatorservice.domain.Ticker;
import com.aggregator.service.aggregatorservice.domain.TradeAction;

import java.time.LocalDateTime;

public record PriceUpdate(Ticker ticker, Integer price, LocalDateTime time) {}
