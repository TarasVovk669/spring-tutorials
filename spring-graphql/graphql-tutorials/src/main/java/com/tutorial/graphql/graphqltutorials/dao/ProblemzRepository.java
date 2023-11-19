package com.tutorial.graphql.graphqltutorials.dao;

import com.tutorial.graphql.graphqltutorials.domain.Problemz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProblemzRepository extends CrudRepository<Problemz, UUID> {

    List<Problemz> findAllByOrderByCreatedDateTimeDesc();

    @Query("select p from Problemz  p where " +
            "upper(p.content) like upper(:keyword) " +
            "or upper(p.title) like upper(:keyword) " +
            "or upper(p.tags) like upper(:keyword) ")
    List<Problemz> findByKey(@Param("keyword") String key);
}
