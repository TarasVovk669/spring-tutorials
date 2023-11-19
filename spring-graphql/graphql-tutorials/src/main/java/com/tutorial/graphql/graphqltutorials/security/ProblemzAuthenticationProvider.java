package com.tutorial.graphql.graphqltutorials.security;

import com.tutorial.graphql.graphqltutorials.dao.UserzRepository;
import com.tutorial.graphql.graphqltutorials.domain.Userz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class ProblemzAuthenticationProvider implements AuthenticationProvider {

    private final UserzRepository userzRepository;

    public ProblemzAuthenticationProvider(UserzRepository userzRepository) {
        this.userzRepository = userzRepository;
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        var user = userzRepository.findUserByToken(auth.getCredentials().toString())
                .orElse(new Userz());

        return new UsernamePasswordAuthenticationToken(user, auth.getCredentials().toString(),
                getAuthorities(user.getUserRole()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String userRole) {
        var authorities = new ArrayList<GrantedAuthority>();

        if (StringUtils.isNotBlank(userRole)) {
            authorities.add(new SimpleGrantedAuthority(userRole));
        }

        return authorities;
    }

}
