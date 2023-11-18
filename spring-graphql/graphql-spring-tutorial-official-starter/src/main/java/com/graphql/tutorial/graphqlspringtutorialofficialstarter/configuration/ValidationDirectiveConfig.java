package com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.validation.rules.OnValidationErrorStrategy;
import graphql.validation.rules.ValidationRules;
import graphql.validation.schemawiring.ValidationSchemaWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Reader;

@Configuration
public class ValidationDirectiveConfig {

    @Bean
    public ValidationSchemaWiring validationSchemaWiring() {
        /** Contains the default validation @Directive constraints */
        var validationRules =
                ValidationRules.newValidationRules()
                        .onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL)
                        .build();

        /** Rewrites your data fetchers so that its arguments are validated prior method execution */
        return new ValidationSchemaWiring(validationRules);
    }
}
