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
    @Query("update t_user u set u.balance = u.balance - :amount where u.id = :userId and u.balance >= :amount")
    Mono<Boolean> updateUserBalance(@Param("userId") Long userId, @Param("amount") Integer amount);
}
