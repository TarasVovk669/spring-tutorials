package com.micro.exchange.currencyexchangeservice.controller;

import com.micro.exchange.currencyexchangeservice.domain.Exchange;
import com.micro.exchange.currencyexchangeservice.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

    @Value("${server.port}")
    private Integer port;

    private final ExchangeRepository exchangeRepository;

    private static final Logger log = LoggerFactory.getLogger(ExchangeController.class);

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public Exchange currencyExchange(@PathVariable("from") String from,
                                     @PathVariable("to") String to) {

        log.info("Get ce from:{} to: {}", from,to);

        return exchangeRepository.findByFromAndTo(from, to)
                .map(x -> {
                    x.setEnv(port.toString());
                    return x;
                })
                .orElseThrow(() -> new RuntimeException("Not Found!"));
    }
}
