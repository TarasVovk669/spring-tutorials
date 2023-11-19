package com.tutorial.graphql.graphqltutorials.dao;

import com.tutorial.graphql.graphqltutorials.domain.Solutionz;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionzRepository extends CrudRepository<Solutionz, UUID> {

    @Query("select s from Solutionz  s where " +
            "upper(s.content) like upper(:keyword)")
    List<Solutionz> findByKey(@Param("keyword") String key);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update solutionz set vote_as_bad_count= vote_as_bad_count+1 where id = :id")
    void addVoteBadCount(@Param("id") UUID id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update solutionz set vote_as_good_count= vote_as_good_count+1 where id = :id")
    void addVoteGoodCount(@Param("id") UUID id);

}
