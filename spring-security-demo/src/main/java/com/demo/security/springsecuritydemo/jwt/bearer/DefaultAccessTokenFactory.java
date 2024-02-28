package com.demo.security.springsecuritydemo.jwt.bearer;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class DefaultAccessTokenFactory implements Function<Token, Token> {

    private Duration tokenTtl = Duration.ofMinutes(5);

    @Override
    public Token apply(Token token) {
        var now = Instant.now();
        return new Token(token.id(), token.subject(),
                token.authorities()
                        .stream()
                        .filter(a -> a.startsWith("GRANT_"))
                        .map(a -> a.substring("GRANT_".length()))
                        .toList(),
                now,
                now.plus(tokenTtl));
    }

    public DefaultAccessTokenFactory tokenTtl(Duration tokenTtl) {
        this.tokenTtl = tokenTtl;
        return this;
    }
}
