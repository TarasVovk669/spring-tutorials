package com.graphql.tutorials.productservice.service;

import com.graphql.tutorials.productservice.domain.Series;
import com.graphql.tutorials.productservice.generated.types.SeriesInput;

import java.util.List;
import java.util.Optional;

public interface SeriesQueryService {
    List<Series> findSeries(Optional<SeriesInput> optionalSeriesInput);
}
