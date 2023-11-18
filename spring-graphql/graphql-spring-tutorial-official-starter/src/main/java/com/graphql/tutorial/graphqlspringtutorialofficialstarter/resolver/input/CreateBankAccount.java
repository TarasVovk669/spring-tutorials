package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver.input;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class CreateBankAccount {

    @NotNull
    @NotBlank
    private String clientFistName;
    private String clientLastName;
    private OffsetDateTime birthdate;
    private Integer clientAge;
    private Currency currency;
    private List<AssetsInput> assetsInput;
    private BalanceInput balance;
}
