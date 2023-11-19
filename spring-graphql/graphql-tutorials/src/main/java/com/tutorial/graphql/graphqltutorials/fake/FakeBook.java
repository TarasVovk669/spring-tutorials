package com.tutorial.graphql.graphqltutorials.fake;

import com.tutorial.graphql.graphqltutorials.generated.types.Address;
import com.tutorial.graphql.graphqltutorials.generated.types.Author;
import com.tutorial.graphql.graphqltutorials.generated.types.Book;
import com.tutorial.graphql.graphqltutorials.generated.types.ReleaseHistory;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class FakeBook {

    private final Faker faker;

    public final static List<Book> BOOKS_LIST = new ArrayList<>();

    public FakeBook(Faker faker) {
        this.faker = faker;
    }

    @PostConstruct
    private void postConstruct(){
        for (int i = 0; i < 20; i++) {
            var addresses = new ArrayList<Address>();
            var author = Author.newBuilder()
                    .name(faker.book().author())
                    .originCountry(faker.country().name())
                    .build();
            var released = ReleaseHistory.newBuilder()
                    .printedEdition(faker.bool().bool())
                    .releasedCountry(faker.country().name())
                    .year(faker.number().numberBetween(2019, 2021))
                    .build();
            var book = Book.newBuilder().author(author)
                    .publisher(faker.book().publisher())
                    .title(faker.book().title())
                    .released(released)
                    .build();

            for (int j = 0; j < ThreadLocalRandom.current().nextInt(1, 3); j++) {
                var address = Address.newBuilder()
                        .country(faker.address().country())
                        .city(faker.address().cityName())
                        .country(faker.address().country())
                        .street(faker.address().streetAddress())
                        .zipCode(faker.address().zipCode())
                        .build();

                addresses.add(address);
            }

            BOOKS_LIST.add(book);
        }
    }
}
