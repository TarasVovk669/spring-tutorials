package com.aggregator.service.aggregatorservice.dto;

import com.aggregator.service.aggregatorservice.domain.Ticker;
import com.aggregator.service.aggregatorservice.domain.TradeAction;

public record TradeRequest(Ticker ticker, TradeAction action, Integer quantity) {}
