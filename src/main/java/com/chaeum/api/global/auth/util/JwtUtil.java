package com.chaeum.api.global.auth.util;

import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final SecretKey secretKey;

    public String createJwt(String category, String email, Role role, Long expiredMs) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredMs);

        return Jwts.builder()
            .claim("category", category)
            .claim("email", email)
            .claim("role", role.name())
            .issuedAt(now)
            .expiration(expiry)
            .signWith(secretKey)
            .compact();
    }

    public String getEmail(String token) {
        return parseClaims(token).get("email", String.class);
    }

    public String getCategory(String token) {
        return parseClaims(token).get("category", String.class);
    }

    public Role getRole(String token) {
        String roleValue = parseClaims(token).get("role", String.class);
        return Role.valueOf(roleValue);
    }

    public boolean isExpired(String token) {
        return parseClaims(token)
            .getExpiration()
            .before(new Date());
    }

    public boolean validateToken(String token) {
        parseClaims(token);
        return true;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {
            throw ChaeumException.from(ErrorCode.EXPIRED_AUTH_TOKEN);
        } catch (io.jsonwebtoken.security.SignatureException e) {
            throw ChaeumException.from(ErrorCode.INVALID_SIGNATURE_TOKEN);
        } catch (IllegalArgumentException e) {
            throw ChaeumException.from(ErrorCode.EMPTY_OR_NULL_TOKEN);
        } catch (JwtException e) {
            throw ChaeumException.from(ErrorCode.UNSUPPORTED_AUTH_TOKEN);
        }
    }
}
