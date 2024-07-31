package com.aggregator.service.aggregatorservice.dto;


import com.aggregator.service.aggregatorservice.domain.Ticker;
import com.aggregator.service.aggregatorservice.domain.TradeAction;

public record StockTradeResponse(Integer customerId,
                                 Ticker ticker,
                                 Integer price,
                                 Integer quantity,
                                 TradeAction action,
                                 Integer totalPrice,
                                 Integer balance) {
}
