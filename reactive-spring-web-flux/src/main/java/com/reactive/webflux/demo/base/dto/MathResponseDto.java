package com.reactive.webflux.demo.base.dto;

import static java.time.ZoneOffset.UTC;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
