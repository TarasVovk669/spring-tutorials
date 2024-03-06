package com.micro.limit.limitserivce.controller;

import com.micro.limit.limitserivce.configuration.LimitConfig;
import com.micro.limit.limitserivce.dto.Limit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitController {

    private final LimitConfig limitConfig;

    @GetMapping("/limits")
    public Limit getLimit(){
        return new Limit(limitConfig.getMin(), limitConfig.getMax());
    }
}
