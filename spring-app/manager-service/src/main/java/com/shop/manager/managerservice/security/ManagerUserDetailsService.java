package com.shop.manager.managerservice.security;

import com.shop.manager.managerservice.domain.Authority;
import com.shop.manager.managerservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class ManagerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user ->
                        User.builder()
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .authorities(
                                        user.getAuthorities().stream()
                                                .map(Authority::getAuthority)
                                        .map(SimpleGrantedAuthority::new).toList())
                                .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


    }
}
