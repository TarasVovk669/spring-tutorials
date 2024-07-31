package com.aggregator.service.aggregatorservice.service;

import com.aggregator.service.aggregatorservice.client.CustomerServiceClient;
import com.aggregator.service.aggregatorservice.client.StockServiceClient;
import com.aggregator.service.aggregatorservice.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerPortfolioService {

  private static final Logger log = LoggerFactory.getLogger(CustomerPortfolioService.class);

  private final CustomerServiceClient customerServiceClient;
  private final StockServiceClient stockServiceClient;

  public CustomerPortfolioService(
      CustomerServiceClient customerServiceClient, StockServiceClient stockServiceClient) {
    this.customerServiceClient = customerServiceClient;
    this.stockServiceClient = stockServiceClient;
  }

  public Mono<CustomerInformation> customerInformationMono(Integer customerId) {
    return customerServiceClient.getCustomerInformation(customerId);
  }

  public Mono<StockTradeResponse> trade(Integer customerId, TradeRequest request) {
    return stockServiceClient
        .stock(request.ticker())
        .map(StockPriceResponse::price)
        .map(
            price ->
                new StockTradeRequest(
                    request.ticker(), price, request.quantity(), request.action()))
        .flatMap(req -> customerServiceClient.trade(customerId, req));
  }
}
