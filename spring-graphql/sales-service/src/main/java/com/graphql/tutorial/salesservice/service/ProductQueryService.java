package com.graphql.tutorial.salesservice.service;

import com.graphql.tutorial.salesservice.generated.types.SimpleModel;

import java.util.Collection;
import java.util.Map;

public interface ProductQueryService {
    Map<Long, SimpleModel> loadSimpleModel(Collection<Long> ids);

    Map<Long, SimpleModel> loadSimpleModelRest(Collection<Long> ids);

}
