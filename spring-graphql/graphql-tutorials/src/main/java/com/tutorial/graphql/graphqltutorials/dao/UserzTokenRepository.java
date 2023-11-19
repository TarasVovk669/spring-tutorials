package com.tutorial.graphql.graphqltutorials.dao;

import com.tutorial.graphql.graphqltutorials.domain.UserzToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserzTokenRepository extends CrudRepository<UserzToken, UUID> {
}
