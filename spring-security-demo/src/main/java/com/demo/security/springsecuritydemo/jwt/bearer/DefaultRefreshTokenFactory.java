package com.demo.security.springsecuritydemo.jwt.bearer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class DefaultRefreshTokenFactory implements Function<Authentication, Token> {

    private Duration tokenTtl = Duration.ofDays(1);

    @Override
    public Token apply(Authentication authentication) {
        var authorities = new ArrayList<String>();
        authorities.add("JWT_REFRESH");
        authorities.add("JWT_LOGOUT");

        authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map("GRANT_"::concat)
                .forEach(authorities::add);

        var now = Instant.now();

        return new Token(UUID.randomUUID(), authentication.getName(), authorities, now, now.plus(tokenTtl));
    }

    public DefaultRefreshTokenFactory tokenTtl(Duration tokenTtl) {
        this.tokenTtl = tokenTtl;
        List<String>ss =  new ArrayList<>();
        return this;
    }
}
