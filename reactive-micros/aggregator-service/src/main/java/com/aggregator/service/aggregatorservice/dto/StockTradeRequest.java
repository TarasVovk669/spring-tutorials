package com.aggregator.service.aggregatorservice.dto;


import com.aggregator.service.aggregatorservice.domain.Ticker;
import com.aggregator.service.aggregatorservice.domain.TradeAction;

public record StockTradeRequest(
        Ticker ticker, Integer price, Integer quantity, TradeAction action) {

  public Integer totalPrice() {
    return price * quantity;
  }
}
