package com.graphql.tutorial.salesservice.service.impl;

import com.graphql.tutorial.salesservice.domain.SalesOrder;
import com.graphql.tutorial.salesservice.generated.types.AddSalesOrderInput;
import com.graphql.tutorial.salesservice.repository.SalesOrderRepository;
import com.graphql.tutorial.salesservice.service.SalesOrderCommandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.graphql.tutorial.salesservice.utils.BaseUtils.map;

@Service
public class SalesOrderCommandServiceImpl implements SalesOrderCommandService {

    private final SalesOrderRepository salesOrderRepository;

    private final ModelMapper modelMapper;


    public SalesOrderCommandServiceImpl(SalesOrderRepository salesOrderRepository, ModelMapper modelMapper) {
        this.salesOrderRepository = salesOrderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SalesOrder addNewSalesOrder(AddSalesOrderInput addSalesOrderInput) {
        var result = map(addSalesOrderInput, SalesOrder.class).apply(modelMapper);

        return salesOrderRepository.save(result);
    }
}
