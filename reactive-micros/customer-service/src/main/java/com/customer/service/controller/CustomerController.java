package com.customer.service.controller;

import com.customer.service.dto.CustomerInformation;
import com.customer.service.dto.StockTradeRequest;
import com.customer.service.dto.StockTradeResponse;
import com.customer.service.service.CustomerPortfolioService;
import com.customer.service.service.TradeService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerPortfolioService customerPortfolioService;
  private final TradeService tradeService;

  public CustomerController(
      CustomerPortfolioService customerPortfolioService, TradeService tradeService) {
    this.customerPortfolioService = customerPortfolioService;
    this.tradeService = tradeService;
  }

  @GetMapping("/{customerId}")
  public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId) {
    return customerPortfolioService.getCustomerInformation(customerId);
  }

  @PostMapping("/{customerId}/trade")
  public Mono<StockTradeResponse> trade(
      @PathVariable Integer customerId,
      @RequestBody Mono<StockTradeRequest> stockTradeRequestMono) {
    return stockTradeRequestMono.flatMap(request -> tradeService.trade(customerId, request));
  }
}
