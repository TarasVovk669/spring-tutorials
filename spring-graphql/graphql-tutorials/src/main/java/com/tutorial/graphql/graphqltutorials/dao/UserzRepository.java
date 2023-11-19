package com.tutorial.graphql.graphqltutorials.dao;

import com.tutorial.graphql.graphqltutorials.domain.Userz;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserzRepository extends CrudRepository<Userz, UUID> {

    Optional<Userz> findByUsernameIgnoreCase(String username);

    @Query(value = "select u from Userz u inner join UserzToken  ut on u.id= ut.userId where ut.token= ?1")
    Optional<Userz> findUserByToken(String token);

    @Transactional
    @Modifying
    @Query("update Userz u set u.active = ?2 where upper(u.username) = upper(?1)")
    void activateUser(String username, boolean isActivate);
}
