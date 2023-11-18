package com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Balance {

    private Long id;
    private BigDecimal amount;
}
