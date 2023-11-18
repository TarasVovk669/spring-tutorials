package com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class JwtUserResponse {
    private String accessToken;
    private Instant expirationDate;
    private String email;
    private Boolean isActive;
    private List<String> roles;
}
