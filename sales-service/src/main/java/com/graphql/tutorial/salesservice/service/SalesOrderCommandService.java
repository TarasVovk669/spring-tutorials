package com.graphql.tutorial.salesservice.service;

import com.graphql.tutorial.salesservice.domain.SalesOrder;
import com.graphql.tutorial.salesservice.generated.types.AddSalesOrderInput;

public interface SalesOrderCommandService {
    SalesOrder addNewSalesOrder( AddSalesOrderInput addSalesOrderInput);
}
