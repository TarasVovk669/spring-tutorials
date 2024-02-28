
package com.demo.security.springsecuritydemo.custom_configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SecurityConfiguration {

    //@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.with(new CustomSecurityConfigurer(), Customizer.withDefaults())
                .build();
    }
}

