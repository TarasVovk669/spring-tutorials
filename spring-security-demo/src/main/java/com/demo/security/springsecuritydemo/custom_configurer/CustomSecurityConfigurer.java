/*
package com.demo.security.springsecuritydemo.custom_configurer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomSecurityConfigurer extends AbstractHttpConfigurer<CustomSecurityConfigurer, HttpSecurity> {

    private String realmName = "simple_realm";

    @Override
    public void init(HttpSecurity builder) throws Exception {
        builder.httpBasic(httpBasic -> httpBasic.realmName(this.realmName))
                .authorizeHttpRequests(amrm -> amrm.anyRequest().authenticated());
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {

    }


    public CustomSecurityConfigurer realmName(String realmName){
        this.realmName = realmName;
        return this;
    }
}
*/
