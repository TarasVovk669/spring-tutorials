package com.tutorial.graphql.graphqltutorials.fake;

import com.tutorial.graphql.graphqltutorials.generated.types.Hello;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakeHello {

    private final Faker faker;

    public final static List<Hello> HELLO_LIST = new ArrayList<>();

    public FakeHello(Faker faker) {
        this.faker = faker;
    }

    @PostConstruct
    private void postConstruct(){
        for(int i=0; i<20; i++){
            HELLO_LIST.add(Hello.newBuilder()
                            .text(faker.company().name())
                            .randomNumber(faker.random().nextInt(5000))
                    .build());
        }
    }
}
