package com.graphql.tutorial.salesservice.service.impl;

import com.graphql.tutorial.salesservice.domain.Customer;
import com.graphql.tutorial.salesservice.generated.types.UniqueCustomerInput;
import com.graphql.tutorial.salesservice.repository.CustomerRepository;
import com.graphql.tutorial.salesservice.repository.specification.CustomerSpecification;
import com.graphql.tutorial.salesservice.service.CustomerQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final CustomerRepository customerRepository;

    public CustomerQueryServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findCustomer(Long id) {
        var specificationId = Specification.where(CustomerSpecification.idEquals(id));
        return customerRepository.findOne(specificationId);
    }

    @Override
    public Optional<Customer> findCustomer(UniqueCustomerInput uniqueCustomerInput) {
        var specificationId = Specification.where(
                null != uniqueCustomerInput.getId()
                        ? CustomerSpecification.idEquals(uniqueCustomerInput.getId()) : null);
        var specificationEmail = Specification.where(
                null != uniqueCustomerInput.getEmail() && !uniqueCustomerInput.getEmail().isBlank()
                        ? CustomerSpecification.emailEqualsIgnoreCase(uniqueCustomerInput.getEmail()) : null);

        return customerRepository.findOne(uniqueCustomerInput.getId() != null ? specificationId : specificationEmail);
    }

    @Override
    public Page<Customer> findCustomersPage(Optional<UniqueCustomerInput> customerInput, Integer page, Integer size) {
        var customer = customerInput.orElse(new UniqueCustomerInput());
        var specificationId = Specification.where(
                null != customer.getId()
                        ? CustomerSpecification.idEquals(customer.getId()) : null);
        var specificationEmail = Specification.where(
                null != customer.getEmail() && !customer.getEmail().isBlank()
                        ? CustomerSpecification.emailEqualsIgnoreCase(customer.getEmail()) : null);

        var pageable = PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(50)
        );

        return customerRepository.findAll(customer.getId() != null ? specificationId : specificationEmail, pageable);
    }
}
