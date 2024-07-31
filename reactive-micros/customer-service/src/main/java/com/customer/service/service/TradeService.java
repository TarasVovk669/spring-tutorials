package com.customer.service.service;

import com.customer.service.dto.StockTradeRequest;
import com.customer.service.dto.StockTradeResponse;
import reactor.core.publisher.Mono;

public interface TradeService {

    Mono<StockTradeResponse> trade(Integer customerId, StockTradeRequest request);
}
