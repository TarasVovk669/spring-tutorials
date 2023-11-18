package com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Assets {

    private Long id;
    private String name;

}
