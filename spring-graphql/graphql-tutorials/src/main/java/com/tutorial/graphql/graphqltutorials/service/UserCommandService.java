package com.tutorial.graphql.graphqltutorials.service;

import com.tutorial.graphql.graphqltutorials.domain.Userz;
import com.tutorial.graphql.graphqltutorials.domain.UserzToken;

import java.util.Optional;

public interface UserCommandService {
    UserzToken login(String username, String password);

    Userz createUser(Userz userz);

    Optional<Userz> activateUser(String userName, boolean isActivate);
}
