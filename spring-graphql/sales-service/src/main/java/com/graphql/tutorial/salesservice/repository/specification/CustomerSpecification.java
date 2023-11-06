package com.graphql.tutorial.salesservice.repository.specification;


import com.graphql.tutorial.salesservice.domain.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_ID = "id";

    public static Specification<Customer> idEquals(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get(FIELD_ID),
                        id
                );
    }

    public static Specification<Customer> emailEqualsIgnoreCase(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get(FIELD_EMAIL)),
                        keyword.toLowerCase()
                );
    }

}
