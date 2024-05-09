package com.demo.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public InMemoryUserDetailsManager memoryUserDetailsManager() {
    var userDetailsJohn =
        User.builder().username("john").password("{noop}pass123").roles("USER").build();
    var userDetailsMary =
        User.builder().username("mary").password("{noop}test123").roles("USER", "MANAGER").build();
    var userDetailsSusan =
        User.builder()
            .username("susan")
            .password("{noop}test321")
            .roles("USER", "MANAGER", "ADMIN")
            .build();

    return new InMemoryUserDetailsManager(userDetailsJohn, userDetailsMary, userDetailsSusan);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(configurer ->
                    configurer
                            .requestMatchers("/").hasRole("USER")
                            .requestMatchers("/leaders").hasRole("MANAGER")
                            .requestMatchers("/system/**").hasRole("ADMIN")
                            .anyRequest().authenticated())
        .formLogin(
            configurer ->
                configurer
                    .loginPage("/showMyCustomLoginForm")
                    .loginProcessingUrl("/authenticateTheUser") // POST data for this URL for processing. This is default for Spring security
                    .permitAll())
            .logout(LogoutConfigurer::permitAll);


    return http.build();
  }
}
