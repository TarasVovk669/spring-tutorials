package com.reactive.webflux.demo.sec01;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "section=01",
        "logging.level.org.springframework.r2dbc=DEBUG"
})
public abstract class AbstractTest {}
