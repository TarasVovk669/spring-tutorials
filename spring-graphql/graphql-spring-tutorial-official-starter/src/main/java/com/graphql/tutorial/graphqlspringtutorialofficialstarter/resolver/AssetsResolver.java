package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.Assets;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.BankAccount;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.StoreData.ASSETS_ACCOUNTS;

@Slf4j
@Component
public class AssetsResolver implements GraphQLResolver<BankAccount> {

    private final Executor executor;

    public AssetsResolver(@Qualifier("SubTaskAsync") Executor executor) {
        this.executor = executor;
    }

    public CompletableFuture<List<Assets>> assets(BankAccount bankAccount) {
        log.info("request assets resolver with bank_account_id: {}", bankAccount.getId());
        return CompletableFuture.supplyAsync(
                () -> ASSETS_ACCOUNTS.get(bankAccount.getId())
                , executor);
    }
}
