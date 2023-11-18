package com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class User {

    private Long id;
    private String email;
    private String password;
    private String fullName;
    private Boolean isActive;
    private List<Role> roles;
}
