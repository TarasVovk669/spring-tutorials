package com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.config;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.details.UserAuthProvider;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.jwt.UserTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final AuthEntryPoint authEntryPoint;
    private final UserAuthProvider userAuthProvider;
    private final UserTokenFilter userTokenFilter;


    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(userAuthProvider)
                .userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.debug(false)
                .ignoring()
                .requestMatchers(new AntPathRequestMatcher("/subscriptions"),
                        new AntPathRequestMatcher("ws://**"),
                        new AntPathRequestMatcher("/playground/**"),
                        new AntPathRequestMatcher("/vendor/playground/**")
                        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((x) -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((x) -> x.authenticationEntryPoint(authEntryPoint))
                .authorizeHttpRequests((x) ->
                        x.requestMatchers(
                                new AntPathRequestMatcher("/graphql/**")

                        ).permitAll()
                )
                .addFilterBefore(userTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
