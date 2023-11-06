package com.graphql.tutorial.salesservice.configuration;

import com.graphql.tutorial.salesservice.domain.Address;
import com.graphql.tutorial.salesservice.domain.Customer;
import com.graphql.tutorial.salesservice.domain.Finance;
import com.graphql.tutorial.salesservice.domain.Loan;
import com.graphql.tutorial.salesservice.domain.SalesOrder;
import com.graphql.tutorial.salesservice.domain.SalesOrderItem;
import com.graphql.tutorial.salesservice.domain.*;
import com.graphql.tutorial.salesservice.generated.types.*;
import com.graphql.tutorial.salesservice.service.CustomerQueryService;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.graphql.tutorial.salesservice.utils.BaseUtils.map;

@Configuration
public class ModelMapperConfiguration {

    private final CustomerQueryService customerQueryService;

    public ModelMapperConfiguration(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        modelMapper.typeMap(AddCustomerInput.class, Customer.class).setConverter(mc -> {
                    mc.getDestination().setEmail(mc.getSource().getEmail());
                    mc.getDestination().setPhone(mc.getSource().getPhone());
                    mc.getDestination().setFullName(mc.getSource().getFullName());
                    mc.getDestination().setBirthDate(mc.getSource().getBirthDate());
                    mc.getDestination().setAddresses(
                            mc.getSource().getAddresses() != null
                                    ? mc.getSource().getAddresses().stream()
                                    .map(e -> map(e, Address.class).apply(modelMapper))
                                    .collect(Collectors.toList())
                                    : Collections.emptyList()
                    );
                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(AddAddressInput.class, Address.class).setConverter(mc -> {
                    mc.getDestination().setCity(mc.getSource().getCity());
                    mc.getDestination().setStreet(mc.getSource().getStreet());
                    mc.getDestination().setZipcode(mc.getSource().getZipcode());
                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(Customer.class, com.graphql.tutorial.salesservice.generated.types.Customer.class).setConverter(mc -> {

                    mc.getDestination().setId(mc.getSource().getId());
                    mc.getDestination().setEmail(mc.getSource().getEmail());
                    mc.getDestination().setPhone(mc.getSource().getPhone());
                    mc.getDestination().setFullName(mc.getSource().getFullName());
                    mc.getDestination().setBirthDate(mc.getSource().getBirthDate());
                    mc.getDestination().setAddresses(
                            mc.getSource().getAddresses() == null || mc.getSource().getAddresses().isEmpty() ? Collections.emptyList()
                                    : mc.getSource().getAddresses().stream()
                                    .map(e -> map(e, com.graphql.tutorial.salesservice.generated.types.Address.class).apply(modelMapper))
                                    .collect(Collectors.toList()));
                    mc.getDestination().setDocuments(mc.getSource().getDocuments().stream()
                            .map(e -> map(e, Document.class).apply(modelMapper))
                            .collect(Collectors.toList()));
                    mc.getDestination().setSalesOrders(mc.getSource().getSalesOrders().stream()
                            .map(e -> map(e, com.graphql.tutorial.salesservice.generated.types.SalesOrder.class).apply(modelMapper))
                            .collect(Collectors.toList()));


                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(SalesOrder.class, com.graphql.tutorial.salesservice.generated.types.SalesOrder.class).setConverter(mc -> {
                    mc.getDestination().setId(mc.getSource().getId());
                    mc.getDestination().setOrderNumber(RandomStringUtils.randomAlphanumeric(10));
                    mc.getDestination().setFinance(map(mc.getSource().getFinance(), com.graphql.tutorial.salesservice.generated.types.Finance.class).apply(modelMapper));
                    mc.getDestination().setSalesOrderItems(mc.getSource().getSalesOrderItems().stream()
                            .map(e -> map(e, com.graphql.tutorial.salesservice.generated.types.SalesOrderItem.class).apply(modelMapper))
                            .collect(Collectors.toList()));
                    mc.getDestination().setOrderDateTime(mc.getSource().getCreatedDateTime());
                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(SalesOrderItem.class, com.graphql.tutorial.salesservice.generated.types.SalesOrderItem.class).setConverter(mc -> {
                    mc.getDestination().setId(mc.getSource().getId());
                    mc.getDestination().setQuantity(mc.getSource().getQuantity());
                    mc.getDestination().setModelId(mc.getSource().getModelId());

                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(Finance.class, com.graphql.tutorial.salesservice.generated.types.Finance.class).setConverter(mc -> {
                    mc.getDestination().setId(mc.getSource().getId());
                    mc.getDestination().setBaseAmount(mc.getSource().getBaseAmount().doubleValue());
                    mc.getDestination().setDiscountAmount(mc.getSource().getDiscountAmount().doubleValue());
                    mc.getDestination().setTaxAmount(mc.getSource().getTaxAmount().doubleValue());
                    mc.getDestination().setIsLoan(mc.getSource().isLoan());
                    mc.getDestination().setLoan(map(mc.getSource().getLoan(), com.graphql.tutorial.salesservice.generated.types.Loan.class).apply(modelMapper));

                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(Loan.class, com.graphql.tutorial.salesservice.generated.types.Loan.class).setConverter(mc -> {
                    mc.getDestination().setId(mc.getSource().getId());
                    mc.getDestination().setFinanceCompany(mc.getSource().getFinanceCompany());
                    mc.getDestination().setContactPersonEmail(mc.getSource().getContactPersonEmail());
                    mc.getDestination().setContactPersonName(mc.getSource().getContactPersonName());
                    mc.getDestination().setContactPersonPhone(mc.getSource().getContactPersonPhone());
                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(Address.class, com.graphql.tutorial.salesservice.generated.types.Address.class).setConverter(mc -> {
                    mc.getDestination().setId(mc.getSource().getId());
                    mc.getDestination().setCity(mc.getSource().getCity());
                    mc.getDestination().setStreet(mc.getSource().getStreet());
                    mc.getDestination().setZipcode(mc.getSource().getZipcode());
                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(CustomerDocument.class, Document.class).setConverter(mc -> {
                    mc.getDestination().setId(mc.getSource().getId());
                    mc.getDestination().setDocumentPath(mc.getSource().getDocumentPath());
                    mc.getDestination().setDocumentType(mc.getSource().getDocumentType());
                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(AddSalesOrderInput.class, SalesOrder.class).setConverter(mc -> {
                    mc.getDestination().setOrderNumber(RandomStringUtils.randomAlphanumeric(10));
                    mc.getDestination().setFinance(map(mc.getSource().getFinance(), Finance.class).apply(modelMapper));
                    mc.getDestination().setCustomer(customerQueryService.findCustomer(mc.getSource()
                            .getCustomerId()).orElseThrow(() -> new DgsEntityNotFoundException("Customer not found")));
                    mc.getDestination().setSalesOrderItems(mc.getSource().getSalesOrderItems().stream()
                            .map(e -> map(e, SalesOrderItem.class).apply(modelMapper))
                            .collect(Collectors.toList()));

                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(SalesOrderItemInput.class, SalesOrderItem.class).setConverter(mc -> {
                    mc.getDestination().setQuantity(mc.getSource().getQuantity());
                    mc.getDestination().setModelId(mc.getSource().getModelId());

                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(FinanceInput.class, Finance.class).setConverter(mc -> {
                    mc.getDestination().setBaseAmount(BigDecimal.valueOf(mc.getSource().getBaseAmount()));
                    mc.getDestination().setDiscountAmount(BigDecimal.valueOf(mc.getSource().getDiscountAmount()));
                    mc.getDestination().setTaxAmount(BigDecimal.valueOf(mc.getSource().getTaxAmount()));
                    mc.getDestination().setLoan(mc.getSource().getIsLoan());
                    mc.getDestination().setLoan(map(mc.getSource().getLoan(), Loan.class).apply(modelMapper));

                    return mc.getDestination();
                }
        );

        modelMapper.typeMap(LoanInput.class, Loan.class).setConverter(mc -> {
                    mc.getDestination().setFinanceCompany(mc.getSource().getFinanceCompany());
                    mc.getDestination().setContactPersonEmail(mc.getSource().getContactPersonEmail());
                    mc.getDestination().setContactPersonName(mc.getSource().getContactPersonName());
                    mc.getDestination().setContactPersonPhone(mc.getSource().getContactPersonPhone());
                    return mc.getDestination();
                }
        );


        return modelMapper;
    }
}
