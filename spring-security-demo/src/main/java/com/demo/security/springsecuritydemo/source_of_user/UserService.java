package com.demo.security.springsecuritydemo.source_of_user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.demo.security.springsecuritydemo.source_of_user.User daoUser = userRepository.findUserByEmail(username).orElseThrow();

        return User.builder()
                .username(daoUser.getEmail())
                .password(daoUser.getPassword())
                .authorities(daoUser.getAuthorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList())
                .build();
    }
}
