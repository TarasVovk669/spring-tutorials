package com.tutorial.graphql.graphqltutorials.exception;

import com.netflix.graphql.types.errors.ErrorDetail;
import com.netflix.graphql.types.errors.ErrorType;

public class ProblemzErrorDetail implements ErrorDetail {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.UNAUTHENTICATED;
    }

    @Override
    public String toString() {
        return "User validation failed, check username or password";
    }
}
