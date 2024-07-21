package com.customer.service.dto;

import com.customer.service.domain.Ticker;
import com.customer.service.domain.TradeAction;

public record StockTradeRequest(
    Ticker ticker, Integer price, Integer quantity, TradeAction action) {

  public Integer totalPrice() {
    return price * quantity;
  }
}
