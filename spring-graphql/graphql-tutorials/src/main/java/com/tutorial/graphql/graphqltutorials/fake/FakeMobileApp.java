package com.tutorial.graphql.graphqltutorials.fake;

import com.tutorial.graphql.graphqltutorials.generated.types.Address;
import com.tutorial.graphql.graphqltutorials.generated.types.Author;
import com.tutorial.graphql.graphqltutorials.generated.types.MobileApp;
import com.tutorial.graphql.graphqltutorials.generated.types.MobileAppCategory;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class FakeMobileApp {

    private final Faker faker;

    public final static List<MobileApp> MOBILE_APPS_LIST = new ArrayList<>();

    public FakeMobileApp(Faker faker) {
        this.faker = faker;
    }

    @PostConstruct
    private void postConstruct() throws MalformedURLException {
        for (int i = 0; i < 20; i++) {
            var addresses = new ArrayList<Address>();
            var author = Author.newBuilder()
                    .name(faker.book().author())
                    .originCountry(faker.country().name())
                    .build();
            var mobileApp = MobileApp.newBuilder()
                    .name(faker.app().name())
                    .author(author)
                    .version(faker.app().version())
                    .platform(randomMobileAppPlatform())
                    .appId(UUID.randomUUID().toString())
                    .releaseDate(LocalDate.now().minusDays(faker.random().nextInt(365)))
                    .download(faker.number().numberBetween(1, 1_500_000))
                    .homepage(new URL("https://" + faker.internet().url()))
                    .category(MobileAppCategory.values()[
                            faker.random().nextInt(MobileAppCategory.values().length)]
                    )
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

            MOBILE_APPS_LIST.add(mobileApp);
        }
    }

    private List<String> randomMobileAppPlatform() {
        switch (ThreadLocalRandom.current().nextInt(10)%3){
            case 0:
                return List.of("Android");
            case 1:
                return List.of("IOS");
            default:
                return List.of("IOS","Android");
        }
    }
}
