package com.tutorial.graphql.graphqltutorials.fake;

import com.tutorial.graphql.graphqltutorials.generated.types.Cat;
import com.tutorial.graphql.graphqltutorials.generated.types.Dog;
import com.tutorial.graphql.graphqltutorials.generated.types.Pet;
import com.tutorial.graphql.graphqltutorials.generated.types.PetFoodType;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakePet {

    private final Faker faker;

    public final static List<Pet> PETS_LIST = new ArrayList<>();

    public FakePet(Faker faker) {
        this.faker = faker;
    }

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 10; i++) {
            Pet animal = switch (i % 2) {
                case 0:
                    yield Dog.newBuilder().name(faker.dog().name())
                            .food(PetFoodType.OMNI)
                            .breed(faker.dog().breed())
                            .size(faker.dog().size())
                            .coatLength(faker.dog().coatLength())
                            .build();
                default:
                    yield Cat.newBuilder().name(faker.cat().name())
                            .food(PetFoodType.CARN)
                            .breed(faker.cat().breed())
                            .registry(faker.cat().registry())
                            .build();
            };

            PETS_LIST.add(animal);
        }
    }
}
