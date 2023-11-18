package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.Balance;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.BankAccount;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class BalanceResolver implements GraphQLResolver<BankAccount> {

    private final Executor executor;

    public BalanceResolver(@Qualifier("SubTaskAsync") Executor executor) {
        this.executor = executor;
    }

    public CompletableFuture<Balance> balance(BankAccount bankAccount, DataFetchingEnvironment env) {
        log.info("request balance resolver with bank_account_id: {}", bankAccount.getId());

        DataLoader<Long, Balance> balanceDataLoader = env.getDataLoader("balanceDataLoader");
        return balanceDataLoader.load(bankAccount.getId());
    }
}
