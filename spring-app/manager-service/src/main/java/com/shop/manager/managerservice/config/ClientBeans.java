package com.shop.manager.managerservice.config;

import com.shop.manager.managerservice.client.RestCatalogueClient;
import com.shop.manager.managerservice.security.ClientRequestInterceptor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean //better use final realization of interface in signature, because of GraalVM
    public RestCatalogueClient restCatalogueClient(
            @Value("${manager-service.catalog-service.url}") String url,
            @Value("${manager-service.catalog-service.registration-id}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        return new RestCatalogueClient(
                RestClient.builder()
                        .baseUrl(url)
                        .requestInterceptor(
                                new ClientRequestInterceptor(
                                        new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository),
                                        registrationId))
                        .build());
    }

    /*@Bean //better use final realization of interface in signature, because of GraalVM
    public RestCatalogueClient restCatalogueClient(
            @Value("${manager-service.catalog-service.url}") String url,
            @Value("${manager-service.catalog-service.username}") String username,
            @Value("${manager-service.catalog-service.password}") String password) {
        return new RestCatalogueClient(
                RestClient.builder()
                        .baseUrl(url)
                        .requestInterceptor(new BasicAuthenticationInterceptor(username, password))
                        .build());
    }*/
}
