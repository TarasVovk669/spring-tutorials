package com.graphql.tutorials.productservice.service;

import com.graphql.tutorials.productservice.domain.Model;
import com.graphql.tutorials.productservice.generated.types.ModelInput;
import com.graphql.tutorials.productservice.generated.types.ModelSimple;
import com.graphql.tutorials.productservice.generated.types.NumericComparisonInput;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ModelQueryService {
    List<Model> findModels(Optional<ModelInput> optionalInput, Optional<NumericComparisonInput> numericInput);

    Page<Model> findModels(Optional<ModelInput> input,
                           Optional<NumericComparisonInput> optNumericInput,
                           Integer page,
                           Integer size);

    List<ModelSimple> getSimpleModels(List<Long> modelIds);
}
