package com.chaeum.api.global.utils;

import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
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

    public boolean validateToken(String token) {
        getClaims(token); // 내부에서 예외 발생 시 throw or 성공
        return true;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {
            throw ChaeumException.from(ErrorCode.EXPIRED_AUTH_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw ChaeumException.from(ErrorCode.UNSUPPORTED_AUTH_TOKEN);
        } catch (io.jsonwebtoken.security.SignatureException e) {
            throw ChaeumException.from(ErrorCode.INVALID_SIGNATURE_TOKEN);
        } catch (IllegalArgumentException e) {
            throw ChaeumException.from(ErrorCode.EMPTY_OR_NULL_TOKEN);
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
