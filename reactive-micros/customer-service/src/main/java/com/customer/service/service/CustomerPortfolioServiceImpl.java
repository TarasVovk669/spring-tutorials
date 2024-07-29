package com.customer.service.service;

import com.customer.service.domain.Customer;
import com.customer.service.dto.CustomerInformation;
import com.customer.service.exceptions.ApplicationExceptions;
import com.customer.service.mapper.EntityDtoMapper;
import com.customer.service.repository.CustomerRepository;
import com.customer.service.repository.PortfolioItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerPortfolioServiceImpl implements CustomerPortfolioService {

  private final CustomerRepository customerRepository;
  private final PortfolioItemRepository portfolioRepository;

  public CustomerPortfolioServiceImpl(
      CustomerRepository customerRepository, PortfolioItemRepository portfolioRepository) {
    this.customerRepository = customerRepository;
    this.portfolioRepository = portfolioRepository;
  }

  @Override
  public Mono<CustomerInformation> getCustomerInformation(Integer customerId) {
    return this.customerRepository
        .findById(customerId)
        .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
        .flatMap(this::build);
  }

  public Mono<CustomerInformation> build(Customer customer) {
    return portfolioRepository
        .findAllByCustomerId(customer.getId())
        .collectList()
        .map(x -> EntityDtoMapper.customerInformation(customer, x));
  }
}
