package com.aggregator.service.aggregatorservice.controller;

import com.aggregator.service.aggregatorservice.dto.CustomerInformation;
import com.aggregator.service.aggregatorservice.dto.StockTradeResponse;
import com.aggregator.service.aggregatorservice.dto.TradeRequest;
import com.aggregator.service.aggregatorservice.service.CustomerPortfolioService;
import com.aggregator.service.aggregatorservice.validator.RequestValidator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerPortfolioController {

  private final CustomerPortfolioService customerPortfolioService;

  public CustomerPortfolioController(CustomerPortfolioService customerPortfolioService) {
    this.customerPortfolioService = customerPortfolioService;
  }

  @GetMapping("/{customerId}")
  public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId) {
    return this.customerPortfolioService.customerInformationMono(customerId);
  }

  @PostMapping("/{customerId}/trade")
  public Mono<StockTradeResponse> trade(
      @PathVariable Integer customerId, @RequestBody Mono<TradeRequest> mono) {
    return mono.transform(RequestValidator.validate())
        .flatMap(req -> this.customerPortfolioService.trade(customerId, req));
  }
}
