package com.demo.security.springsecuritydemo.jwt.bearer;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.function.Function;


public class RefreshTokenJweStringSerializer implements Function<Token, String> {

    private static final Logger log = LoggerFactory.getLogger(RefreshTokenJweStringSerializer.class);

    private final JWEEncrypter jweEncrypter;

    private JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;

    private EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    public RefreshTokenJweStringSerializer(JWEEncrypter jweEncrypter) {
        this.jweEncrypter = jweEncrypter;
    }

    public RefreshTokenJweStringSerializer(JWEEncrypter jweEncrypter,
                                           JWEAlgorithm jweAlgorithm,
                                           EncryptionMethod encryptionMethod) {
        this.jweEncrypter = jweEncrypter;
        this.jweAlgorithm = jweAlgorithm;
        this.encryptionMethod = encryptionMethod;
    }

    @Override
    public String apply(Token token) {
        var jweHeader = new JWEHeader.Builder(this.jweAlgorithm,encryptionMethod)
                .keyID(token.id().toString())
                .build();
        var claimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiredAt()))
                .claim("authorities", token.authorities())
                .build();
        var signedJWT = new EncryptedJWT(jweHeader, claimsSet);

        try {
            signedJWT.encrypt(this.jweEncrypter);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Error: ", e);

        }
        return null;
    }
}
