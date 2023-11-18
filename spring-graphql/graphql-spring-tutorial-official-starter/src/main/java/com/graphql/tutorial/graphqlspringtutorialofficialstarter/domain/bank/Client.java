package com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;


@Data
@Builder
public class Client {

    private Long id;
    private String fistName;
    private String lastName;
    private OffsetDateTime birthdate;
    private Integer age;

}
