package com.tutorial.graphql.graphqltutorials.exception.handler;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import com.tutorial.graphql.graphqltutorials.exception.ProblemzAuthException;
import com.tutorial.graphql.graphqltutorials.exception.ProblemzErrorDetail;
import com.tutorial.graphql.graphqltutorials.exception.ProblemzPermissionException;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ProblemzHandler implements DataFetcherExceptionHandler {

    private final DefaultDataFetcherExceptionHandler defaultDataFetcherExceptionHandler = new DefaultDataFetcherExceptionHandler();

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
        var exeption = handlerParameters.getException();

        if (exeption instanceof ProblemzAuthException) {
            var grapError = TypedGraphQLError.newBuilder().message(exeption.getMessage()).path(handlerParameters.getPath())
                    .errorDetail(new ProblemzErrorDetail()).build();

            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(grapError).build());
        } else if (exeption instanceof ProblemzPermissionException) {
            var grapError = TypedGraphQLError.newBuilder().message(exeption.getMessage())
                    .path(handlerParameters.getPath())
                    .errorType(ErrorType.PERMISSION_DENIED)
                    .build();

            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(grapError).build());
        }


        return defaultDataFetcherExceptionHandler.handleException(handlerParameters);
    }
}
