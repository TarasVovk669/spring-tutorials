package com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.jwt;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration.security.account.details.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class UserJwtUtils {

    @Value("${jwt.jwtAccountSecretKey:786E96A90030FF58429B2751AC1BDAAC}")
    private String jwtSecret;
    
    @Value("${jwt.jwtExpirationMs: 1500000}")
    private Long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl accountPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
            .setSubject((accountPrincipal.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Date getExpirationFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return true;
        } catch (SignatureException e) {
        log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
        log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
        log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
        log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
        log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public Long getJwtExpirationMs(){
        return jwtExpirationMs;
    }
}
