package com.demo.security.springsecuritydemo.jwt.bearer;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Tokens(String accessToken, String accessTokenExpiry,
                     String refreshToken, String refreshTokenExpiry) {
}
