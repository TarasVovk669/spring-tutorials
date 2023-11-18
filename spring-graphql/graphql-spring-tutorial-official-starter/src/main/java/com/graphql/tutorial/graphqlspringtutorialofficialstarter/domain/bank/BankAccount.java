package com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class BankAccount {

    private Long id;
    private Client client;
    private Currency currency;
    private List<Assets> assets;
    private Balance balance;


}
