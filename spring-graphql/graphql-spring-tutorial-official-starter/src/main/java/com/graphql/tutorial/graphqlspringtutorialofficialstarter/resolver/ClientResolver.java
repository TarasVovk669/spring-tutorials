package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.BankAccount;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.Client;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.StoreData.CLIENT_ACCOUNTS;

@Slf4j
@Component
public class ClientResolver implements GraphQLResolver<BankAccount> {

    private final Executor executor;

    public ClientResolver(@Qualifier("SubTaskAsync") Executor executor) {
        this.executor = executor;
    }

    public CompletableFuture<Client> client(BankAccount bankAccount) {
        log.info("request client resolver with bank_account_id: {}", bankAccount.getId());
        return CompletableFuture.supplyAsync(
                () -> CLIENT_ACCOUNTS.get(bankAccount.getId()), executor);
    }
}
