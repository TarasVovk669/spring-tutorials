package com.shop.manager.managerservice.client;


import com.shop.manager.managerservice.domain.Product;
import com.shop.manager.managerservice.dto.ProductPayload;
import com.shop.manager.managerservice.exception.CustomBadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RestCatalogueClient implements CatalogueClient {

    private static final ParameterizedTypeReference<List<Product>> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final RestClient restClient;

    @Override
    public List<Product> getAllProducts() {
        return restClient.get().uri("/catalogue-api/products")
                .retrieve().body(TYPE_REFERENCE);
    }

    @Override
    public Product create(String name, String description, BigDecimal price) {
        try {
            return restClient.post()
                    .uri("catalogue-api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ProductPayload(null, name, description, price))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest e) {
            ProblemDetail problemDetail = e.getResponseBodyAs(ProblemDetail.class);
            throw new CustomBadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Product getProductById(Long id) {
        try {
            return Optional.ofNullable(restClient.get().uri("/catalogue-api/products/{id}", id)
                    .retrieve().body(Product.class)).orElseThrow();

        } catch (HttpClientErrorException.NotFound e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @Override
    public void update(Long id, String name, String description, BigDecimal price) {
        try {
            restClient.patch()
                    .uri("/catalogue-api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ProductPayload(id, name, description, price))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest e) {
            ProblemDetail problemDetail = e.getResponseBodyAs(ProblemDetail.class);

            throw new RuntimeException((String) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Optional.of(restClient.delete().uri("/catalogue-api/products/{id}", id)
                    .retrieve().toBodilessEntity());

        } catch (HttpClientErrorException.NotFound e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }
}
