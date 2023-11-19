package com.tutorial.graphql.graphqltutorials.datasource.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.tutorial.graphql.graphqltutorials.fake.FakeStock;
import com.tutorial.graphql.graphqltutorials.generated.types.Stock;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.time.Duration;

@DgsComponent
public class FakeStockDataResolver {

    @Autowired
    private FakeStock fakeStock;

    @DgsSubscription
    public Publisher<Stock> randomStock(){
        return Flux.interval(Duration.ofSeconds(1)).map(t -> fakeStock.randomStock());
    }
}
