package com.micro.conversion.currencyconversionservice.controller;

import com.micro.conversion.currencyconversionservice.dto.CurrencyConversion;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

    @Value("${server.port}")
    private Integer port;

    private final RestTemplate restTemplate;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion currencyExchange(@PathVariable("from") String from,
                                               @PathVariable("to") String to,
                                               @PathVariable("quantity") Integer quantity) {

        var pathVariables = Map.of(
                "from", from,
                "to", to

        );

        var resp = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                pathVariables).getBody();

        return CurrencyConversion.builder()
                .id(resp.getId())
                .from(resp.getFrom())
                .to(resp.getTo())
                .env(resp.getEnv())
                .conversionMultiple(resp.getConversionMultiple())
                .quantity(quantity)
                .totalCalculatedAmount(resp.getConversionMultiple().multiply(BigDecimal.valueOf(quantity)))
                .build();
    }
}
