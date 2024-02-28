/*
package com.demo.security.springsecuritydemo.custom_filter;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class HexConfigurer extends AbstractHttpConfigurer<HexConfigurer, HttpSecurity> {

    private AuthenticationEntryPoint authenticationEntryPoint =
            (request, response, authException) -> {
                response.addHeader(AUTHORIZATION, "Hex");
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            };

    @Override
    public void init(HttpSecurity builder) throws Exception {
        builder.exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint));
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        final var authManager = builder.getSharedObject(AuthenticationManager.class);
        final var customAuthFilter = new AuthenticationFilter(authManager, new HexAuthConverter());

        customAuthFilter.setSuccessHandler((request, response, authentication) -> {
        }); // continue filters chain
        customAuthFilter.setFailureHandler(new AuthenticationEntryPointFailureHandler(this.authenticationEntryPoint));

        builder.addFilterBefore(customAuthFilter, BasicAuthenticationFilter.class);
    }


}
*/
