package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver.input;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceInput {
    private BigDecimal amount;
}
