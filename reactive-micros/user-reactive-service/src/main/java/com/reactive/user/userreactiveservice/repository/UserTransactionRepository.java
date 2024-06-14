package com.reactive.user.userreactiveservice.repository;

import com.reactive.user.userreactiveservice.domain.User;
import com.reactive.user.userreactiveservice.domain.UserTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction,Long> {

    Flux<UserTransaction> findAllByUserId(Long userId);
}
