package com.micro.conversion.currencyconversionservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@ToString
@Builder
public class CurrencyConversion {

    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionMultiple;
    private String env;
    private Integer quantity;
    private BigDecimal totalCalculatedAmount;
}
