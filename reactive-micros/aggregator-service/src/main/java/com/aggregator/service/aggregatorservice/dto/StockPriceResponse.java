package com.aggregator.service.aggregatorservice.dto;

import com.aggregator.service.aggregatorservice.domain.Ticker;
import com.aggregator.service.aggregatorservice.domain.TradeAction;

public record StockPriceResponse(Ticker ticker, Integer price) {}
