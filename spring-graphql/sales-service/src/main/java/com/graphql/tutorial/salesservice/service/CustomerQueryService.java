package com.graphql.tutorial.salesservice.service;

import com.graphql.tutorial.salesservice.domain.Customer;
import com.graphql.tutorial.salesservice.generated.types.UniqueCustomerInput;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CustomerQueryService {

    Optional<Customer> findCustomer(Long id);
    Optional<Customer> findCustomer(UniqueCustomerInput uniqueCustomerInput);

    Page<Customer> findCustomersPage(Optional<UniqueCustomerInput> customerInput, Integer page, Integer size);
}
