package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidationResolver implements GraphQLQueryResolver {

    public String validation(String fullName, Integer age, Integer range, String email){
        log.info("validate data: fullName{}, age:{}, range: {}, email:{}", fullName,age,range,email);

        return "Hello world!";
    }
}
