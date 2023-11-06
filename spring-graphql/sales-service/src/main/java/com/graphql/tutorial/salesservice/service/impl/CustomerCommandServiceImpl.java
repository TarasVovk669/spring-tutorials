package com.graphql.tutorial.salesservice.service.impl;

import com.graphql.tutorial.salesservice.domain.Address;
import com.graphql.tutorial.salesservice.domain.Customer;
import com.graphql.tutorial.salesservice.domain.CustomerDocument;
import com.graphql.tutorial.salesservice.generated.types.AddAddressInput;
import com.graphql.tutorial.salesservice.generated.types.AddCustomerInput;
import com.graphql.tutorial.salesservice.generated.types.UpdateCustomerInput;
import com.graphql.tutorial.salesservice.repository.CustomerRepository;
import com.graphql.tutorial.salesservice.service.CustomerCommandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.graphql.tutorial.salesservice.utils.BaseUtils.map;

@Service
public class CustomerCommandServiceImpl implements CustomerCommandService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerCommandServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Customer addNewCustomer(AddCustomerInput input) {
        return customerRepository.save(map(input, Customer.class).apply(modelMapper));
    }

    @Override
    public Customer addAddressToExistingCustomer(Customer customer, List<AddAddressInput> addAddressInputList) {
        customer.getAddresses().addAll(addAddressInputList.stream().map(a -> map(a, Address.class).apply(modelMapper)).toList());
        return customerRepository.save(customer);
    }

    @Override
    public Customer addFile(Customer customer, MultipartFile multipartFile, String type) {
        String path = "src/main/resources/".concat(Optional.ofNullable(multipartFile.getOriginalFilename()).orElse(multipartFile.getName()));

        customer.getDocuments().add(CustomerDocument.CustomerDocumentBuilder.aCustomerDocument()
                .documentPath("http://dummy-upload/v1/" + path.replace(" ", ""))
                .documentType(type)
                .build());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateExistingCustomer(Customer customer, UpdateCustomerInput customerUpdate) {
        customer.setFullName(null != customerUpdate.getFullName() ? customerUpdate.getFullName() : customer.getFullName());
        customer.setPhone(null != customerUpdate.getPhone() ? customerUpdate.getPhone() : customer.getPhone());
        customer.setEmail(null != customerUpdate.getEmail() ? customerUpdate.getEmail() : customer.getEmail());
        customer.setBirthDate(null != customerUpdate.getBirthDate() ? customerUpdate.getBirthDate() : customer.getBirthDate());

        return customerRepository.save(customer);
    }


}
