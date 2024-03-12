package com.micro.exchange.currencyexchangeservice.controller;

import com.micro.exchange.currencyexchangeservice.repository.ExchangeRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Resilience4jController {

    private final static Logger log = LoggerFactory.getLogger(Resilience4jController.class);

    @GetMapping("/example/{id}")
    @Retry(name = "name-give-example", fallbackMethod = "hardcodedResponse")
    public String example(@PathVariable("id") Long id){
        log.info("Step into method with id: {}", id);
        if(id % 2 == 0){
            var dummy = new RestTemplate().getForEntity("http://localhost:9999/dummy",String.class);
            log.info("{}",dummy);
        }

        return "Example_v1";
    }

    //https://resilience4j.readme.io/docs/circuitbreaker
    @GetMapping("/circuit-example/{id}")
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    @RateLimiter(name = "rate-circuit-limiter",fallbackMethod = "rateLimiterDefaultMethod")
    public String exampleCircuitBreaker(@PathVariable("id") Long id){
        log.info("Step into circuit method with id: {}", id);
        if(id % 2 == 0){
            var dummy = new RestTemplate().getForEntity("http://localhost:9999/dummy",String.class);
            log.info("{}",dummy);
        }

        return "Example_v1";
    }

    @GetMapping("/circuit-example/{id}")
    @Bulkhead(name = "bulkhead-example")
    public String exampleBulkhead(@PathVariable("id") Long id){
        log.info("Step into circuit method with id: {}", id);
        if(id % 2 == 0){
            var dummy = new RestTemplate().getForEntity("http://localhost:9999/dummy",String.class);
            log.info("{}",dummy);
        }

        return "Example_v1";
    }

    private String hardcodedResponse(Exception e){
        return "hardcoded_response_v1";
    }

    private String rateLimiterDefaultMethod(Exception e){
        return "rateLimiterDefaultMethod";
    }
}
