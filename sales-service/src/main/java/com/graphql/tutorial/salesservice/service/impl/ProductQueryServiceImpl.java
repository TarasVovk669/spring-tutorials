package com.graphql.tutorial.salesservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphql.tutorial.salesservice.client.ProductClient;
import com.graphql.tutorial.salesservice.client.ProductRestClient;
import com.graphql.tutorial.salesservice.generated.types.SimpleModel;
import com.graphql.tutorial.salesservice.service.ProductQueryService;
import com.graphql.tutorial.salesservice.utils.Const;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductClient productClient;
    private final ProductRestClient productRestClient;
    private final ObjectMapper objectMapper;

    public ProductQueryServiceImpl(ProductClient productClient, ProductRestClient productRestClient, ObjectMapper objectMapper) {
        this.productClient = productClient;
        this.productRestClient = productRestClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<Long, SimpleModel> loadSimpleModel(Collection<Long> ids) {
        var resultList = productClient.fetchGraphQLResponse(
                Const.QUERY_SIMPLE_MODELS,
                Const.OPERATION_NAME_SIMPLE_MODELS,
                Map.of(Const.VARIABLE_NAME_MODEL_UUIDS, ids));

        try {
            var jsonNode = objectMapper.readTree(resultList.getJson());
            var simpleModelsJson = jsonNode.get("data").get("simpleModels").toString();
            var listSimpleModels = objectMapper.readValue(
                    simpleModelsJson,
                    new TypeReference<List<SimpleModel>>() {
                    }
            );

            return listSimpleModels.stream().collect(Collectors.toMap(SimpleModel::getId,
                    Function.identity()));
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    @Override
    public Map<Long, SimpleModel> loadSimpleModelRest(Collection<Long> ids) {
        var resultList = productRestClient.fetchRestSimpleModels(ids);

        return resultList.stream().collect(Collectors.toMap(SimpleModel::getId,
                Function.identity()));
    }
}

