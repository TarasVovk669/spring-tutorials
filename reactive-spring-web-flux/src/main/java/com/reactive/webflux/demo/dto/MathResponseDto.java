package com.reactive.webflux.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

@Data
@ToString
@NoArgsConstructor
public class MathResponseDto {

    private LocalDateTime localDateTime;

    private Integer result;

    public MathResponseDto(Integer result) {
        this.result = result;
        localDateTime = LocalDateTime.now(UTC);
    }
}
