package com.micro.gateway.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingResponseFilter implements GlobalFilter {

    private final Logger log = LoggerFactory.getLogger(LoggingResponseFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Path -> {}, response: {}",
                exchange.getRequest().getPath(),
                exchange.getResponse().getStatusCode());
            

        return chain.filter(exchange);
    }
}
