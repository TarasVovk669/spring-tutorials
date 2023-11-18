package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.BankAccount;
import graphql.execution.DataFetcherResult;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.*;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.StoreData.BANK_ACCOUNTS;

@Slf4j
@Component
public class BankAccountResolver implements GraphQLQueryResolver {

    @PreAuthorize("hasAuthority('ADMIN')")
    public DataFetcherResult<BankAccount> bankAccount(Long id, DataFetchingEnvironment environment) throws IOException {
        log.info("request bankAccount resolver with id: {}", id);

        return DataFetcherResult.<BankAccount>newResult()
                .data(BANK_ACCOUNTS.get(id))
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public BankAccount bankAccountSelectionSet(Long id,
                                               DataFetchingEnvironment environment // this is graphql context
    ) {
        log.info("request bankAccount resolver with id: {}", id);

        // this is the set of fields from query we will return to user
        // we can make our logic based on some fields
        var sSet = environment.getSelectionSet();

        if (sSet.containsAllOf("id", "currency")) {
            // add some logic
            log.info("contains some logic here");
        }

        return BANK_ACCOUNTS.get(id);
    }

    public Connection<BankAccount> bankAccounts(Integer page,
                                                @DefaultValue(value = "20") Integer size,
                                                DataFetchingEnvironment env) {
        log.info("Requesting bankAccounts page: {} size: {}", page, size);

        List<List<BankAccount>> pages = getPages(BANK_ACCOUNTS.values(), size);

        if (page < 0 || page >= pages.size()) {
            return new DefaultConnection<>(Collections.emptyList(), new DefaultPageInfo(null, null, false, false));
        }

        List<Edge<BankAccount>> edges = pages.get(page).stream()
                .map(bankAccount -> new DefaultEdge<>(bankAccount, new DefaultConnectionCursor(bankAccount.getId().toString())))
                .collect(Collectors.toList());

        boolean hasNextPage = page < (pages.size() - 1);
        boolean hasPreviousPage = page > 0;
        PageInfo pageInfo = new DefaultPageInfo(
                edges.isEmpty() ? null : edges.get(0).getCursor(),
                edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor(),
                hasPreviousPage,
                hasNextPage
        );

        return new DefaultConnection<>(edges, pageInfo);

    }

    public static <T> List<List<T>> getPages(Collection<T> c, Integer pageSize) {
        if (c == null)
            return Collections.emptyList();
        List<T> list = new ArrayList<T>(c);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
        int numPages = (int) Math.ceil((double) list.size() / (double) pageSize);
        List<List<T>> pages = new ArrayList<List<T>>(numPages);
        for (int pageNum = 0; pageNum < numPages; )
            pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        return pages;
    }


}
