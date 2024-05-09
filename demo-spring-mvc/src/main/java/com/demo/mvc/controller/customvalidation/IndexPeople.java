package com.demo.mvc.controller.customvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IndexPeopleValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IndexPeople {

    String value() default "IDX";

    String message() default "Must start with IDX";

    //can group related constaints
    Class<?>[] groups() default {};

    //Provide custom details about validation failure(severity level, error code)
    Class<? extends Payload>[] payload() default {};
}
