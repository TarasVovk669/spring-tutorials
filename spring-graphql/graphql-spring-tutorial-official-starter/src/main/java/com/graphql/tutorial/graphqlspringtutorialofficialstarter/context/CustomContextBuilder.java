
package com.graphql.tutorial.graphqlspringtutorialofficialstarter.context;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.dataloader.BalanceDataLoader;
import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.execution.context.GraphQLKickstartContext;
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;
import jakarta.websocket.server.HandshakeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomContextBuilder implements GraphQLServletContextBuilder {

    private final BalanceDataLoader balanceDataLoader;

    public CustomContextBuilder(BalanceDataLoader balanceDataLoader) {
        this.balanceDataLoader = balanceDataLoader;
    }

    @Override
    public GraphQLKickstartContext build() {
        return new DefaultGraphQLContext(balanceDataLoader.createDataLoaderRegistry());
    }

    @Override
    public GraphQLKickstartContext build(HttpServletRequest request, HttpServletResponse httpServletResponse) {

        return new DefaultGraphQLContext(balanceDataLoader.createDataLoaderRegistry());
    }

    @Override
    public GraphQLKickstartContext build(Session session, HandshakeRequest handshakeRequest) {
        Map<Object, Object> map = new HashMap<>();
        map.put(Session.class, session);
        map.put(HandshakeRequest.class, handshakeRequest);
        return GraphQLKickstartContext.of(map);

    }

}

