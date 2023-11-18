package com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.StoreData.USERS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return UserDetailsImpl.build(Optional.ofNullable(USERS.get(email))
                .orElseThrow(() -> new RuntimeException("User not found")));
    }
}
