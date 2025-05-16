package com.chaeum.api.global.utils;

import com.chaeum.api.domain.member.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public String getEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    public String getCategory(String token) {
        return extractClaims(token).get("category", String.class);
    }

    public Role getRole(String token) {
        return Role.valueOf(extractClaims(token).get("role", String.class));
    }

    public Boolean isExpired(String token) {
        return extractClaims(token)
            .getExpiration()
            .before(new Date(System.currentTimeMillis()));
    }

    // 5. JWT 토큰 생성
    public String createJwt(String category, String email, Role role, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("email", email)
                .claim("role", role.name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    // 6. 토큰 검증 로직
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.warn("JWT 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
