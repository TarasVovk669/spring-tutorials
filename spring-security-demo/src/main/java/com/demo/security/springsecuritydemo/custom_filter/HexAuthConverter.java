
package com.demo.security.springsecuritydemo.custom_filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class HexAuthConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        final var auth = request.getHeader(AUTHORIZATION);

        if (auth != null && auth.startsWith("Hex")) {
            final var rawToken = auth.replaceAll("^Hex ", "");
            final var token = new String(Hex.decode(rawToken), StandardCharsets.UTF_8);
            final var tokenParts = token.split(":");

            return UsernamePasswordAuthenticationToken.unauthenticated(
                    tokenParts[0],
                    tokenParts[1]);
        }

        return null;
    }
}

