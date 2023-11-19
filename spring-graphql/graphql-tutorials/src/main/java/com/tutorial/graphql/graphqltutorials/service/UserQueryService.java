package com.tutorial.graphql.graphqltutorials.service;

import com.tutorial.graphql.graphqltutorials.domain.Userz;

import java.util.Optional;

public interface UserQueryService {
    Optional<Userz> findUserzByToken(String token);
}
