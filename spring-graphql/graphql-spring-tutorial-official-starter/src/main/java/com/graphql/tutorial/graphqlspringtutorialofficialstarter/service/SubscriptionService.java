package com.graphql.tutorial.graphqlspringtutorialofficialstarter.service;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.BankAccount;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;


@Slf4j
@Service
public class SubscriptionService {

    private final FluxProcessor<BankAccount, BankAccount> processor;
    private final FluxSink<BankAccount> sink;

    public SubscriptionService() {
        this.processor = DirectProcessor.<BankAccount>create().serialize();
        this.sink = processor.sink();
    }


    public void publish(BankAccount bankAccount) {
        sink.next(bankAccount);
    }

    public Publisher<BankAccount> bankAccounts() {
        return processor.map(bankAccount -> {
            log.info("Publishing global subscription update for Bank Account {}", bankAccount);
            return bankAccount;
        });
    }

    public Publisher<BankAccount> bankAccount(Long id) {
        return processor
                .filter(bankAccount -> id.equals(bankAccount.getId()))
                .map(bankAccount -> {
                    log.info("Publishing individual subscription update for Bank Account {}", bankAccount);
                    return bankAccount;
                });
    }
}
