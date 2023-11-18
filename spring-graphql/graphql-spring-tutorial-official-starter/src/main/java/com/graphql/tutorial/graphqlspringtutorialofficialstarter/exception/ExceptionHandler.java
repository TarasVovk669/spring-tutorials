package com.graphql.tutorial.graphqlspringtutorialofficialstarter.exception;

import graphql.GraphQLException;
import graphql.kickstart.spring.error.ThrowableGraphQLError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintDeclarationException;
import java.io.IOException;

@Slf4j
@Component
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({GraphQLException.class, ConstraintDeclarationException.class})
    public ThrowableGraphQLError handle(Exception e){
        log.info("Exception");
        return new ThrowableGraphQLError(e);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
    public ThrowableGraphQLError handle(IOException e){
        log.info("IOException");
        return new ThrowableGraphQLError(e);
    }
}
