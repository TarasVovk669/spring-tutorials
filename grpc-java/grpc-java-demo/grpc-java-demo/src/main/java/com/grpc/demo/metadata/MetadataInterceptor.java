package com.grpc.demo.metadata;

import io.grpc.*;

import java.util.Objects;

import static com.grpc.demo.metadata.Role.ADMIN;
import static com.grpc.demo.metadata.Role.USER;

public class MetadataInterceptor implements ServerInterceptor {

    private static final Metadata.Key<String> key = Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER);
    public static final Context.Key<Role> KEY = Context.key("auth-role");
    public static final Context.Key<Role> KEY1 = Context.key("auth-role");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        var authToken = headers.get(key);

        System.out.println("Token: " + authToken);
        if (validateToken(authToken)) {
            Role role = extractRole(authToken);
            var context = Context.current()
                    .withValue(KEY, role);
            return Contexts.interceptCall(context, call, headers, next);
            //return next.startCall(call, headers);
        } else {
            call.close(Status.UNAUTHENTICATED, headers);
        }
        return null;
    }

    private static boolean validateToken(String token) {
        return Objects.nonNull(token) && token.contains("qwerty123");
    }

    private static Role extractRole(String jwt) {
        return jwt.endsWith(USER.name()) ? USER : ADMIN;
    }
}
