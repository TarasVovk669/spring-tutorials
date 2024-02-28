/*
package com.demo.security.springsecuritydemo.custom_filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

//@Configuration
public class SecurityConfiguration {

    //@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(new CurlDeniedFilter(), DisableEncodeUrlFilter.class)
                .authorizeHttpRequests(x -> x.anyRequest().authenticated())
                .with(new HexConfigurer(),Customizer.withDefaults())
                .build();
    }
}
*/
