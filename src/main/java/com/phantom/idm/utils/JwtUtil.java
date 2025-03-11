package com.phantom.idm.utils;

import com.phantom.idm.dto.response.auth.AuthTokenResponse;
import com.phantom.idm.repository.UserDetailJpaRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMinute}")
    private int jwtExpirationInMs;

    @Value("${jwt.expirationJwtRefreshMinute}")
    private int jwtRefreshExpirationInMs;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    private final UserDetailJpaRepository userDetailJpaRepository;

    public JwtUtil(UserDetailJpaRepository userDetailJpaRepository) {
        this.userDetailJpaRepository = userDetailJpaRepository;
    }

    private String generateJwt(String subject, long expirationInMinutes) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(Map.of(
                        "sub", subject,
                        "iss", jwtIssuer,
                        "iat", now.getEpochSecond(),
                        "exp", now.plus(expirationInMinutes, ChronoUnit.MINUTES).getEpochSecond()
                ))
                .signWith(key)
                .compact();
    }

    public AuthTokenResponse generateToken(String subject) {

        if (Objects.isNull(subject)) {
            throw new IllegalArgumentException("Subject tidak boleh kosong");
        }

        var user = userDetailJpaRepository.findByEmail(subject).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        
        return AuthTokenResponse.builder()
                .token(generateJwt(user.getEmail(), jwtExpirationInMs))
                .refreshToken(generateJwt(user.getEmail(), jwtRefreshExpirationInMs))
                .exp(Date.from(Instant.now().plus(jwtExpirationInMs, ChronoUnit.MINUTES)))
                .build();
    }

}
