package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.User;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver.input.RegisterInput;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.StoreData.USERS;

@Slf4j
@Component
public class RegisterResolver implements GraphQLMutationResolver {

    public User registration(RegisterInput input) {
        log.info("Register: {}", input);

        if (null == USERS.get(input.getEmail())) {
            var user = User.builder()
                    .id(new Random().nextLong())
                    .fullName(input.getFullName())
                    .password(input.getPassword())
                    .roles(input.getRoles())
                    .email(input.getEmail())
                    .isActive(input.getIsActive())
                    .build();

            USERS.put(input.getEmail(), user);
            return user;
        } else {
            throw new RuntimeException("Users already exists");
        }
    }
}
