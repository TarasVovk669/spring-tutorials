package com.micro.limit.limitserivce.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties("limit-service")
public class LimitConfig {
    private Integer min;
    private Integer max;


}
