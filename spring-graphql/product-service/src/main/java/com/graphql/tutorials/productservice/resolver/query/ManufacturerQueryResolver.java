package com.graphql.tutorials.productservice.resolver.query;

import com.graphql.tutorials.productservice.domain.Manufacturer;
import com.graphql.tutorials.productservice.generated.types.ManufacturerInput;
import com.graphql.tutorials.productservice.service.ManufacturerQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Optional;


@DgsComponent
public class ManufacturerQueryResolver {

    private final ManufacturerQueryService manufacturerQueryService;

    public ManufacturerQueryResolver(ManufacturerQueryService manufacturerQueryService) {
        this.manufacturerQueryService = manufacturerQueryService;
    }


    @DgsQuery
    public List<Manufacturer> manufacturers(@InputArgument Optional<ManufacturerInput> manufacturerInput) {
        return manufacturerQueryService.getManufacturers(manufacturerInput);
    }

    @DgsQuery
    public Connection<Manufacturer> manufacturersPagination(@InputArgument Optional<ManufacturerInput> manufacturerInput,
                                                            DataFetchingEnvironment env,
                                                            @InputArgument Integer first,
                                                            @InputArgument Integer last,
                                                            @InputArgument String after,
                                                            @InputArgument String before) {
        var fullResult = manufacturerQueryService.getManufacturers(manufacturerInput);

        return new SimpleListConnection<>(fullResult).get(env);
    }
}
