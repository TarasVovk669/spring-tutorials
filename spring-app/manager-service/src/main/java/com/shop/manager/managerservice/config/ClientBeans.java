package com.shop.manager.managerservice.config;

import com.shop.manager.managerservice.client.RestCatalogueClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean //better use final realization of interface in signature, because of GraalVM
    public RestCatalogueClient restCatalogueClient(@Value("${manager-service.catalog-service.url}") String url) {

        return new RestCatalogueClient(
                RestClient.builder()
                        .baseUrl(url)
                        .build());
    }
}
