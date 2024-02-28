package com.demo.security.springsecuritydemo.jwt.bearer;


import com.demo.security.springsecuritydemo.source_of_user.DeactivatedTokenRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.time.Instant;

public class TokenAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final DeactivatedTokenRepository deactivatedTokenRepository;

    public TokenAuthenticationUserDetailsService(DeactivatedTokenRepository deactivatedTokenRepository) {
        this.deactivatedTokenRepository = deactivatedTokenRepository;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticationToken)
            throws UsernameNotFoundException {

        if (authenticationToken.getPrincipal() instanceof Token token) {
            return new TokenUser(token.subject(), "pass123", true, true,
                    !deactivatedTokenRepository.existsById(token.id()) &&
                            token.expiredAt().isAfter(Instant.now()),
                    true,
                    token.authorities().stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList(), token);
        }

        throw new UsernameNotFoundException("Principal must me of type Token");
    }
}
