package com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.details.UserDetailsImpl;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.jwt.JwtUserResponse;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.jwt.UserJwtUtils;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.User;
import com.graphql.tutorial.graphqlspringtutorialofficialstarter.resolver.input.LoginInput;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.StoreData.USERS;

@Slf4j
@Component
public class LoginResolver implements GraphQLQueryResolver {

    private final AuthenticationManager authenticationManager;
    private final UserJwtUtils userJwtUtils;

    public LoginResolver(AuthenticationManager authenticationManager, UserJwtUtils userJwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userJwtUtils = userJwtUtils;
    }

    public JwtUserResponse login(LoginInput login) {
        log.info("Login: {}", login);
        return Optional.ofNullable( USERS.get(login.getEmail()))
                .map(u -> {
                    if (!u.getPassword().equalsIgnoreCase(login.getPassword())) {
                        throw new RuntimeException("Password is incorrect");
                    }

                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
                    String token = userJwtUtils.generateJwtToken(authentication);
                    UserDetailsImpl accountDetails = (UserDetailsImpl) authentication.getPrincipal();
                    List<String> roles = accountDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList();

                    return new JwtUserResponse(
                            token,
                            userJwtUtils.getExpirationFromJwtToken(token).toInstant(),
                            accountDetails.getUsername(),
                            accountDetails.getIsActive(),
                            roles
                    );
                })
                .orElseThrow(() -> new RuntimeException("User not found"));

    }

    public User me(DataFetchingEnvironment env){
        log.info("current user");

        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication != null) {
            var userDetails = (String) authentication.getPrincipal();
            // Use the username to fetch the user details
            // Assuming userService has a method to fetch user by username
            return USERS.get(userDetails);
        }

        return null; // or throw an exception if the user is not found


    }
}
