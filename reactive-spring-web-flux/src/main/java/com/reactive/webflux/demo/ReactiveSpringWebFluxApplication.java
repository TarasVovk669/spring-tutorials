package com.reactive.webflux.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.reactive.webflux.demo.${section}")
@EnableR2dbcRepositories(basePackages = "com.reactive.webflux.demo.${section}")
public class ReactiveSpringWebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringWebFluxApplication.class, args);
    }

}
