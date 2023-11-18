package com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.jwt;

import java.io.IOException;




import com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.details.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;


@Slf4j
@Component
public class UserTokenFilter extends OncePerRequestFilter{

    private final UserJwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public UserTokenFilter(UserJwtUtils jwtUtils, UserDetailsServiceImpl accountDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = accountDetailsService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
        throws ServletException, IOException {
        try {
            log.info("Account Auth - Request URL path: {}, Request content type: {}",request.getRequestURI(), request.getContentType());
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String email = jwtUtils.getUsernameFromJwtToken(jwt);
                UserDetails accountDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null,
                    accountDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Successfully set account authentication: {}", email);
            }
        } catch (Exception e) {
            log.error("Cannot set account authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
          return headerAuth.substring(7);
        }
        return null;
    }
}
