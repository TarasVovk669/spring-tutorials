package com.reactive.user.userreactiveservice.repository;

import com.reactive.user.userreactiveservice.domain.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,Long> {

    @Modifying
    @Query("UPDATE t_user SET balance = (balance - :amount) WHERE id = :userId AND balance >= :amount")
    Mono<Integer> updateUserBalance(@Param("userId") Long userId, @Param("amount") Integer amount);
}
