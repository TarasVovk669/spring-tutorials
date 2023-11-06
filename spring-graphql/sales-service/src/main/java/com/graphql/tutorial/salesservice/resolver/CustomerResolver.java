package com.graphql.tutorial.salesservice.resolver;

import com.graphql.tutorial.salesservice.generated.DgsConstants;
import com.graphql.tutorial.salesservice.generated.types.*;
import com.graphql.tutorial.salesservice.service.CustomerCommandService;
import com.graphql.tutorial.salesservice.service.CustomerQueryService;
import com.graphql.tutorial.salesservice.service.ProductQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.graphql.tutorial.salesservice.utils.BaseUtils.map;

@DgsComponent
public class CustomerResolver {

    private final CustomerQueryService customerQueryService;
    private final CustomerCommandService customerCommandService;
    private final ProductQueryService productQueryService;
    private final ModelMapper modelMapper;


    public CustomerResolver(CustomerQueryService customerQueryService, CustomerCommandService customerCommandService, ProductQueryService productQueryService, ModelMapper modelMapper) {
        this.customerQueryService = customerQueryService;
        this.customerCommandService = customerCommandService;
        this.productQueryService = productQueryService;
        this.modelMapper = modelMapper;
    }

    @DgsMutation
    public CustomerMutationResponse addNewCustomer(@InputArgument AddCustomerInput customer) {

        var saved = customerCommandService.addNewCustomer(customer);
        return CustomerMutationResponse.newBuilder()
                .customerId(saved.getId())
                .success(true)
                .message(saved.getFullName().concat(" was created"))
                .build();
    }

    @DgsMutation
    public CustomerMutationResponse addAddressesToExistingCustomer(@InputArgument UniqueCustomerInput customer,
                                                                   @InputArgument List<AddAddressInput> addresses) {
        var customerF = customerQueryService.findCustomer(customer)
                .orElseThrow(() -> new DgsEntityNotFoundException("Customer not found"));

        var updated = customerCommandService.addAddressToExistingCustomer(customerF, addresses);

        return CustomerMutationResponse.newBuilder()
                .customerId(updated.getId())
                .success(true)
                .message(updated.getFullName().concat(" was updated"))
                .build();
    }

    @DgsQuery
    public CustomerPagination customerPagination(DataFetchingEnvironment env,
                                                 @InputArgument Optional<UniqueCustomerInput> customer,
                                                 @InputArgument Integer page,
                                                 @InputArgument Integer size) {


        Page<Customer> customerPage = customerQueryService.findCustomersPage(customer, page, size)
                .map(e -> map(e, Customer.class).apply(modelMapper));

        var listCustomerAsGraphql = Optional.ofNullable(customerPage.getContent())
                .orElse(Collections.emptyList()).stream().toList();

        var content = new SimpleListConnection<>(listCustomerAsGraphql).get(env);


        var customerPaginationResult = new CustomerPagination();
        customerPaginationResult.setCustomerConnection(content);
        customerPaginationResult.setPage(customerPage.getNumber());
        customerPaginationResult.setSize(customerPage.getSize());
        customerPaginationResult.setTotalElement(customerPage.getTotalElements());
        customerPaginationResult.setTotalPage(customerPage.getTotalPages());


        return customerPaginationResult;
    }

    @DgsMutation
    public CustomerMutationResponse addDocumentToExistingCustomer(@InputArgument UniqueCustomerInput customer,
                                                                  @InputArgument String documentType,
                                                                  DataFetchingEnvironment env) throws IOException {
        var customerF = customerQueryService.findCustomer(customer)
                .orElseThrow(() -> new DgsEntityNotFoundException("Customer not found"));

        var updated = customerCommandService.addFile(customerF,
                env.getArgument(DgsConstants.MUTATION.ADDDOCUMENTTOEXISTINGCUSTOMER_INPUT_ARGUMENT.DocumentFile), documentType);

        return CustomerMutationResponse.newBuilder()
                .customerId(updated.getId())
                .success(true)
                .message(updated.getFullName().concat(" was upload new file"))
                .build();

    }

    @DgsMutation
    public CustomerMutationResponse updateExistingCustomer(@InputArgument UniqueCustomerInput customer,
                                                           @InputArgument UpdateCustomerInput customerUpdate) {
        var customerF = customerQueryService.findCustomer(customer)
                .orElseThrow(() -> new DgsEntityNotFoundException("Customer not found"));

        var updated = customerCommandService.updateExistingCustomer(customerF, customerUpdate);

        return CustomerMutationResponse.newBuilder()
                .customerId(updated.getId())
                .success(true)
                .message(updated.getFullName().concat(" was updated"))
                .build();
    }
}
