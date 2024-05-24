package com.shop.customer.customerapp.configuration;

import com.shop.customer.customerapp.client.ProductWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Bean
    public ProductWebClient productWebClient(
            @Value("${customer-app.services.catalogue.url}")  String url){
        return new ProductWebClient(WebClient.builder()
                .baseUrl(url)
                .build());
    }
}
