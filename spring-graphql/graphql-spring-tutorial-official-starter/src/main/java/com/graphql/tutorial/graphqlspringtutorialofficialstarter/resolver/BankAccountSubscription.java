package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.BankAccount;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.service.SubscriptionService;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BankAccountSubscription implements GraphQLSubscriptionResolver {

    private final SubscriptionService service;

    public BankAccountSubscription(SubscriptionService service) {
        this.service = service;
    }

    public Publisher<BankAccount> bankAccountsSub() {
        return service.bankAccounts();
    }

    public Publisher<BankAccount> bankAccountSub(Long id) {
        return service.bankAccount(id);
    }
}
