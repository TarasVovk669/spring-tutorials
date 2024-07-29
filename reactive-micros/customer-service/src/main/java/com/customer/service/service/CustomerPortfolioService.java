package com.customer.service.service;

import com.customer.service.dto.CustomerInformation;
import reactor.core.publisher.Mono;

public interface CustomerPortfolioService {

    Mono<CustomerInformation> getCustomerInformation(Integer customerId);
}
