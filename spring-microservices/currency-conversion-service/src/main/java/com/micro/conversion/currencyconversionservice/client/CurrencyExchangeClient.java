package com.micro.conversion.currencyconversionservice.client;


import com.micro.conversion.currencyconversionservice.dto.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange") //eureka will manage it
public interface CurrencyExchangeClient {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion currencyExchange(@PathVariable("from") String from,
                                        @PathVariable("to") String to);
}
