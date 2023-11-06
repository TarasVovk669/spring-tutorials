package com.graphql.tutorials.productservice.resolver.query;

import com.graphql.tutorials.productservice.domain.Series;
import com.graphql.tutorials.productservice.generated.types.SeriesInput;
import com.graphql.tutorials.productservice.service.SeriesQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class SeriesQueryResolver {

    private final SeriesQueryService seriesQueryService;

    public SeriesQueryResolver(SeriesQueryService seriesQueryService) {
        this.seriesQueryService = seriesQueryService;
    }

    @DgsQuery
    public List<Series> series(@InputArgument Optional<SeriesInput> seriesInput) {
        return seriesQueryService.findSeries(seriesInput);
    }

    @DgsQuery
    public Connection<Series> seriesPagination(
            @InputArgument Optional<SeriesInput> seriesInput,
            DataFetchingEnvironment env,
            @InputArgument Integer first,
            @InputArgument Integer last,
            @InputArgument String after,
            @InputArgument String before
    ) {
        var fullResult = seriesQueryService.findSeries(seriesInput);

        return new SimpleListConnection<>(fullResult).get(env);
    }
}
