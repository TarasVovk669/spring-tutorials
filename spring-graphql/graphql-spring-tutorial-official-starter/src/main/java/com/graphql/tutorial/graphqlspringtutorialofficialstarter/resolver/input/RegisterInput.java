package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver.input;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.Role;
import lombok.Data;

import java.util.List;

@Data
public class RegisterInput {
    private String email;
    private String password;
    private String fullName;
    private Boolean isActive;
    private List<Role> roles;
}
