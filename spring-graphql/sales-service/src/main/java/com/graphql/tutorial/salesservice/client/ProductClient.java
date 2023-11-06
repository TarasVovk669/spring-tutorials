package com.graphql.tutorial.salesservice.client;

import com.netflix.graphql.dgs.client.CustomGraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.HttpResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductClient {

    //@Value("${product-service.url}")
    private String URL = "http://localhost:8086/graphql";
    private final RestTemplate restTemplate = new RestTemplate();

    private CustomGraphQLClient graphQLClient = GraphQLClient.createCustom(
            URL, (url, headers, body) -> {
                var httpHeaders = new HttpHeaders();
                headers.forEach(httpHeaders::addAll);

                ResponseEntity<String> exchange = restTemplate.exchange(
                        url, HttpMethod.POST,
                        new HttpEntity<>(body, httpHeaders),
                        String.class
                );

                return new HttpResponse(
                        exchange.getStatusCode().value(),
                        exchange.getBody()
                );
            }
    );

    public GraphQLResponse fetchGraphQLResponse(
            String grahpQLQuery,
            String operationName,
            Map<String, ? extends Object> variablesMap
    ) {
        return graphQLClient.executeQuery(
                grahpQLQuery,
                Optional.ofNullable(variablesMap).orElse(Collections.emptyMap()),
                operationName
        );
    }



}
