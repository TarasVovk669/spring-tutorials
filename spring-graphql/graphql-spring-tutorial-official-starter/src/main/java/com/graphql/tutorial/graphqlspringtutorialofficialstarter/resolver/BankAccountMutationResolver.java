package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.Assets;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.Balance;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.BankAccount;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.Client;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver.input.CreateBankAccount;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.service.SubscriptionService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import static com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.StoreData.*;

@Slf4j
@Component
@Validated // for validation this is mandatory
public class BankAccountMutationResolver implements GraphQLMutationResolver {

    private final SubscriptionService service;

    public BankAccountMutationResolver(SubscriptionService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public BankAccount createBankAccount(@Valid CreateBankAccount input) {
        log.info("mutation bankAccount resolver: {}", input);


        var bankAccount = BankAccount.builder()
                .id(new Random().nextLong())
                .currency(input.getCurrency())
                .build();

        var client = Client.builder()
                .id(new Random().nextLong())
                .fistName(input.getClientFistName())
                .lastName(input.getClientLastName())
                .age(input.getClientAge())
                .birthdate(input.getBirthdate())
                .build();

        var assets = input.getAssetsInput().stream().map(
                e -> Assets.builder()
                        .id(new Random().nextLong())
                        .name(e.getName())
                        .build()
        ).toList();

        var balance = Balance.builder()
                .id(new Random().nextLong())
                .amount(BigDecimal.valueOf(new Random().nextDouble()))
                .build();


        BANK_ACCOUNTS.put(bankAccount.getId(), bankAccount);
        CLIENT_ACCOUNTS.put(bankAccount.getId(), client);
        BALANCE_ACCOUNTS.put(bankAccount.getId(), balance);
        ASSETS_ACCOUNTS.put(bankAccount.getId(), assets);

        service.publish(bankAccount);
        return bankAccount;
    }


    public String uploadFile(DataFetchingEnvironment env) throws ServletException, IOException {
        HttpServletRequest context = env.getGraphQlContext().get(HttpServletRequest.class);
        String file = context.getParts().stream().map(Part::getSubmittedFileName).findFirst().get();

        return "http://dummy/file-path/file/" + file;
    }


}
