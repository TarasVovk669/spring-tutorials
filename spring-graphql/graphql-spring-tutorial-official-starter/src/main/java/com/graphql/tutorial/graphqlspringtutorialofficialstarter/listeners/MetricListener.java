package com.graphql.tutorial.graphqlspringtutorialofficialstarter.listeners;

import graphql.kickstart.servlet.core.GraphQLServletListener;
import graphql.parser.antlr.GraphqlListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
public class MetricListener implements GraphQLServletListener {
    @Override
    public RequestCallback onRequest(HttpServletRequest request, HttpServletResponse response) {
        var start = LocalDateTime.now();
        return new RequestCallback() {
            @Override
            public void onSuccess(HttpServletRequest request, HttpServletResponse response) {
                //log.info("Request is success");
                RequestCallback.super.onSuccess(request, response);
            }

            @Override
            public void onError(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
                //log.info("Request has error");
                RequestCallback.super.onError(request, response, throwable);
            }

            @Override
            public void onFinally(HttpServletRequest request, HttpServletResponse response) {
                //log.info("Request took : {}", Duration.between(start, LocalDateTime.now()));
                RequestCallback.super.onFinally(request, response);
            }
        };
    }
}
