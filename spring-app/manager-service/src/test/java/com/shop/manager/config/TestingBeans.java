package com.shop.manager.config;

import com.shop.manager.managerservice.client.RestCatalogueClient;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

import static org.mockito.Mockito.mock;

@Configuration
public class TestingBeans {

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        return mock(ClientRegistrationRepository.class);
    }

    @Bean
    OAuth2AuthorizedClientRepository authorizedClientRepository() {
        return mock(OAuth2AuthorizedClientRepository.class);
    }

    @Bean
    @Primary
    public RestCatalogueClient restCatalogueClient(
            @Value("${manager-service.catalog-service.url:http://localhost:54321}") String catalogueBaseUri
    ) {
        return new RestCatalogueClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestFactory(new JdkClientHttpRequestFactory())
                .build());
    }
}
