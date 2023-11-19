package com.tutorial.graphql.graphqltutorials.security;

import com.tutorial.graphql.graphqltutorials.dao.UserzRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class ProblemzSecurityConfig {

    private final UserzRepository userzRepository;

    public ProblemzSecurityConfig(UserzRepository userzRepository) {
        this.userzRepository = userzRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var authProvider = new ProblemzAuthenticationProvider(userzRepository);

        http.apply(ProblemzHttpConfigurer.newInstance());
        http.authenticationProvider(authProvider);
        http.csrf()
                .disable().authorizeHttpRequests(
                        auth -> auth.anyRequest().authenticated()
                );

        return http.build();
    }
}

