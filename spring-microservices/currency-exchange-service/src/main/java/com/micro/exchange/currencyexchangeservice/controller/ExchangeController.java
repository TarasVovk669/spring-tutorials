package com.micro.exchange.currencyexchangeservice.controller;

import com.micro.exchange.currencyexchangeservice.domain.Exchange;
import com.micro.exchange.currencyexchangeservice.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;

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

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public Exchange currencyExchange(@PathVariable("from") String from,
                                     @PathVariable("to") String to) {

        return exchangeRepository.findByFromAndTo(from, to)
                .map(x -> {
                    x.setEnv(port.toString());
                    return x;
                })
                .orElseThrow(() -> new RuntimeException("Not Found!"));
    }
}
