package com.graphql.tutorial.salesservice.service;

import com.graphql.tutorial.salesservice.domain.Customer;
import com.graphql.tutorial.salesservice.generated.types.AddAddressInput;
import com.graphql.tutorial.salesservice.generated.types.AddCustomerInput;
import com.graphql.tutorial.salesservice.generated.types.UpdateCustomerInput;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CustomerCommandService {
    Customer addNewCustomer(AddCustomerInput input);

    Customer addAddressToExistingCustomer(Customer customer, List<AddAddressInput> addAddressInputList);

    Customer addFile(Customer customer, MultipartFile multipartFile, String type) throws IOException;

    Customer updateExistingCustomer(Customer customerF, UpdateCustomerInput customerUpdate);
}
