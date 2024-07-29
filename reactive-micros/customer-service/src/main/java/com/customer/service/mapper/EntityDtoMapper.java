package com.customer.service.mapper;

import com.customer.service.domain.Customer;
import com.customer.service.domain.PortfolioItem;
import com.customer.service.domain.Ticker;
import com.customer.service.dto.CustomerInformation;
import com.customer.service.dto.Holding;
import com.customer.service.dto.StockTradeRequest;
import com.customer.service.dto.StockTradeResponse;

import java.util.List;

public class EntityDtoMapper {

  public static CustomerInformation customerInformation(
      Customer customer, List<PortfolioItem> portfolioItems) {
    var holdings =
        portfolioItems.stream().map(i -> new Holding(i.getTicker(), i.getQuantity())).toList();
    return new CustomerInformation(
        customer.getId(), customer.getName(), customer.getBalance(), holdings);
  }

  public static PortfolioItem toPortfolioItem(Integer customerId, Ticker ticker) {
    var item = new PortfolioItem();
    item.setCustomerId(customerId);
    item.setTicker(ticker);
    item.setQuantity(0);

    return item;
  }

  public static StockTradeResponse stockTradeResponse(
      Integer customerId, StockTradeRequest request, Integer balance) {
    return new StockTradeResponse(
        customerId,
        request.ticker(),
        request.price(),
        request.quantity(),
        request.action(),
        request.totalPrice(),
        balance);
  }
}
