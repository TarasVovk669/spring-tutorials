package com.tutorial.graphql.graphqltutorials.datasource.problemz;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.tutorial.graphql.graphqltutorials.generated.DgsConstants;
import com.tutorial.graphql.graphqltutorials.generated.types.*;
import com.tutorial.graphql.graphqltutorials.service.UserCommandService;
import com.tutorial.graphql.graphqltutorials.service.UserQueryService;
import com.tutorial.graphql.graphqltutorials.util.GraphQLMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestHeader;

@DgsComponent
public class UserDataResolver {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UserDataResolver(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @DgsQuery(field = "me")
    public User accountInfo(@RequestHeader(name = "authToken") String token) {
        return GraphQLMapper.mapToGraphQL(userQueryService.findUserzByToken(token).orElseThrow(DgsEntityNotFoundException::new));
    }

    @DgsMutation(field = "userCreate")
    public UserResponse createUser(@InputArgument(name = "user") UserCreateInput createInput) {
        var user = userCommandService.createUser(GraphQLMapper.mapToEntity(createInput));
        return UserResponse.newBuilder().user(GraphQLMapper.mapToGraphQL(user)).build();
    }

    @DgsMutation(field = DgsConstants.MUTATION.UserLogin)
    public UserResponse login(@InputArgument(name = "user") UserLoginInput userLoginInput) {
        var genToken = GraphQLMapper.mapToGraphQL(
                userCommandService.login(userLoginInput.getUsername(), userLoginInput.getPassword()));
        var userInfo = accountInfo(genToken.getAuthToken());

        return UserResponse.newBuilder().user(userInfo).authToken(genToken).build();
    }

    @Secured("ROLE_ADMIN")
    @DgsMutation(field = DgsConstants.MUTATION.UserActivation)
    public UserActivationResponse activation(@InputArgument(name = "user") UserActivationInput input) {
        var updated = userCommandService.activateUser(input.getUsername(), input.getActivate()).orElseThrow(DgsEntityNotFoundException::new);

        return UserActivationResponse.newBuilder()
                .isActive(updated.isActive())
                .build();
    }

}
