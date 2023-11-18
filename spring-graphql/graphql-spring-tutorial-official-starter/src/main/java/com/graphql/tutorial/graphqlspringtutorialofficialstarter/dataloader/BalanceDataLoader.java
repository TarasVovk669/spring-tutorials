package com.graphql.tutorial.graphqlspringtutorialofficialstarter.dataloader;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.Balance;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.StoreData.BALANCE_ACCOUNTS;

@Component
public class BalanceDataLoader {

    public DataLoaderRegistry createDataLoaderRegistry() {
        DataLoaderRegistry registry = new DataLoaderRegistry();

        DataLoader<Long, Balance> balanceDataLoader = DataLoaderFactory.newDataLoader(accountIds ->
                CompletableFuture.supplyAsync(() -> {
                    Map<Long, Balance> balanceMap = BALANCE_ACCOUNTS.entrySet()
                            .stream()
                            .filter(x -> accountIds.contains(x.getKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                    return accountIds.stream().map(balanceMap::get).collect(Collectors.toList());
                })
        );

        registry.register("balanceDataLoader", balanceDataLoader);
        return registry;
    }
}
