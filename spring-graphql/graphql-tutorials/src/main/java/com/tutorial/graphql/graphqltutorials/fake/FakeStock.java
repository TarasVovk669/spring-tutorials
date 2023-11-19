package com.tutorial.graphql.graphqltutorials.fake;

import com.tutorial.graphql.graphqltutorials.generated.types.Stock;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class FakeStock {

    private final Faker faker;

    public FakeStock(Faker faker) {
        this.faker = faker;
    }

   public Stock randomStock(){
        return Stock.newBuilder()
                .lastTradeDateTime(OffsetDateTime.now())
                .price(faker.random().nextInt(100,1000))
                .symbol(faker.stock().nyseSymbol())
                .build();
   }
}
