package com.reactive.webflux.demo.base.dto;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class MultiplyRequestDto {

    private Integer one;
    private Integer two;

}
