package com.graphql.tutorials.productservice.resolver.query;

import com.graphql.tutorials.productservice.domain.Model;
import com.graphql.tutorials.productservice.generated.types.ModelInput;
import com.graphql.tutorials.productservice.generated.types.ModelPagination;
import com.graphql.tutorials.productservice.generated.types.ModelSimple;
import com.graphql.tutorials.productservice.generated.types.NumericComparisonInput;
import com.graphql.tutorials.productservice.service.ModelQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class ModelQueryResolver {

    private final ModelQueryService modelQueryService;

    public ModelQueryResolver(ModelQueryService modelQueryService) {
        this.modelQueryService = modelQueryService;
    }

    @DgsQuery
    public List<Model> models(@InputArgument Optional<ModelInput> modelInput,
                              @InputArgument Optional<NumericComparisonInput> numericInput) {
        return modelQueryService.findModels(modelInput, numericInput);
    }

    @DgsQuery
    public ModelPagination modelsPagination(
            @InputArgument Optional<ModelInput> modelInput,
            @InputArgument Optional<NumericComparisonInput> priceInput,
            DataFetchingEnvironment env,
            @InputArgument Integer page,
            @InputArgument Integer size
    ) {
        var pageModel = modelQueryService.findModels(
                modelInput, priceInput, page, size
        );

        var paginatedResult = new ModelPagination();
        var modelConnection = new SimpleListConnection<>(
                pageModel.getContent()
        ).get(env);

        paginatedResult.setModelConnection(modelConnection);
        paginatedResult.setPage(pageModel.getNumber());
        paginatedResult.setSize(pageModel.getSize());
        paginatedResult.setTotalPage(pageModel.getTotalPages());
        paginatedResult.setTotalElement(pageModel.getTotalElements());

        return paginatedResult;
    }

    @DgsQuery
    public List<ModelSimple> simpleModels(@InputArgument List<Long> modelIds){
        return modelQueryService.getSimpleModels(modelIds);

    }
}
