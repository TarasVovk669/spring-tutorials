package com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScalarConfiguration {

    @Bean
    public GraphQLScalarType nNonNegativeInt(){
        return ExtendedScalars.NonNegativeInt;
    }

    @Bean
    public GraphQLScalarType dateTime(){
        return ExtendedScalars.DateTime;
    }
}
