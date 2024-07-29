package com.customer.service.service;

import com.customer.service.domain.Customer;
import com.customer.service.domain.PortfolioItem;
import com.customer.service.dto.StockTradeRequest;
import com.customer.service.dto.StockTradeResponse;
import com.customer.service.exceptions.ApplicationExceptions;
import com.customer.service.mapper.EntityDtoMapper;
import com.customer.service.repository.CustomerRepository;
import com.customer.service.repository.PortfolioItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TradeServiceImpl implements TradeService {

  private final CustomerRepository customerRepository;
  private final PortfolioItemRepository portfolioItemRepository;

  public TradeServiceImpl(
      CustomerRepository customerRepository, PortfolioItemRepository portfolioItemRepository) {
    this.customerRepository = customerRepository;
    this.portfolioItemRepository = portfolioItemRepository;
  }

  @Override
  public Mono<StockTradeResponse> trade(Integer customerId, StockTradeRequest request) {
    return switch (request.action()) {
      case BUY -> this.buyStock(customerId, request);
      case SELL -> this.sellStock(customerId, request);
    };
  }

  private Mono<StockTradeResponse> sellStock(Integer customerId, StockTradeRequest request) {
    var customerMono =
        customerRepository
            .findById(customerId)
            .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId));

    var portfolioMono =
        this.portfolioItemRepository
            .findAllByCustomerIdAndTicker(customerId, request.ticker())
            .filter(pi -> pi.getQuantity() >= request.quantity())
            .switchIfEmpty(ApplicationExceptions.insufficientShares(customerId));

    return customerMono
        .zipWhen(customer -> portfolioMono)
        .flatMap(t -> executeSell(t.getT1(), t.getT2(), request));
  }

  private Mono<StockTradeResponse> buyStock(Integer customerId, StockTradeRequest request) {
    var customerMono =
        customerRepository
            .findById(customerId)
            .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
            .filter(c -> c.getBalance() >= request.totalPrice())
            .switchIfEmpty(ApplicationExceptions.insufficientBalance(customerId));

    var portfolioMono =
        this.portfolioItemRepository
            .findAllByCustomerIdAndTicker(customerId, request.ticker())
            .defaultIfEmpty(EntityDtoMapper.toPortfolioItem(customerId, request.ticker()));

    return customerMono
        .zipWhen(customer -> portfolioMono)
        .flatMap(t -> executeBuy(t.getT1(), t.getT2(), request));
  }

  private Mono<StockTradeResponse> executeBuy(
      Customer customer, PortfolioItem portfolioItem, StockTradeRequest request) {
    customer.setBalance(customer.getBalance() - request.totalPrice());
    portfolioItem.setQuantity(portfolioItem.getQuantity() + request.quantity());

    var response =
        EntityDtoMapper.stockTradeResponse(customer.getId(), request, customer.getBalance());
    return Mono.zip(
            this.customerRepository.save(customer),
            this.portfolioItemRepository.save(portfolioItem))
        .thenReturn(response);
  }

  private Mono<StockTradeResponse> executeSell(
      Customer customer, PortfolioItem portfolioItem, StockTradeRequest request) {
    customer.setBalance(customer.getBalance() + request.totalPrice());
    portfolioItem.setQuantity(portfolioItem.getQuantity() - request.quantity());

    var response =
        EntityDtoMapper.stockTradeResponse(customer.getId(), request, customer.getBalance());
    return Mono.zip(
            this.customerRepository.save(customer),
            this.portfolioItemRepository.save(portfolioItem))
        .thenReturn(response);
  }
}
