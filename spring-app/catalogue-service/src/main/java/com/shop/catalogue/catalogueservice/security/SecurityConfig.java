package com.shop.catalogue.catalogueservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/catalogue-api/products")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.PATCH, "/catalogue-api/products/{productId:\\d}")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.DELETE, "/catalogue-api/products/{productId:\\d}")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.GET)
                        .hasAuthority("SCOPE_view_catalogue")
                        .anyRequest().denyAll())
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults()))
                .build();
    }

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests ->
                    authorizeHttpRequests.requestMatchers("/catalogue-api/**")
                            .hasRole("SERVICE"))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // off session logic. session creates by servlet container!
                .build();
    }*/

}
