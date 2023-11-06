package com.graphql.tutorial.salesservice.resolver;

import com.graphql.tutorial.salesservice.generated.types.AddSalesOrderInput;
import com.graphql.tutorial.salesservice.generated.types.SalesOrderMutationResponse;
import com.graphql.tutorial.salesservice.service.SalesOrderCommandService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class SalesOrderResolver {

    private final SalesOrderCommandService salesOrderCommandService;


    public SalesOrderResolver(SalesOrderCommandService salesOrderCommandService) {
        this.salesOrderCommandService = salesOrderCommandService;

    }

    @DgsMutation
    public SalesOrderMutationResponse addNewSalesOrder(@InputArgument AddSalesOrderInput salesOrder) {
        var salesOrderRes = salesOrderCommandService.addNewSalesOrder(salesOrder);

        return SalesOrderMutationResponse.newBuilder()
                .customerId(salesOrderRes.getCustomer().getId())
                .success(true)
                .salesOrderId(salesOrderRes.getId())
                .orderNumber(salesOrderRes.getOrderNumber())
                .message(salesOrderRes.getCustomer().getFullName().concat(" create sales_order with id: ").concat(salesOrderRes.getId().toString()))
                .build();
    }
}
