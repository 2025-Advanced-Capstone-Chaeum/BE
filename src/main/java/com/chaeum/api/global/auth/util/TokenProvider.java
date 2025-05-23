package com.chaeum.api.global.auth.util;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.global.properties.JwtProperties;
import com.chaeum.api.global.utils.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    public String generateAccessToken(Member member) {
        return generateAccessToken(member.getId(), member.getEmail(), member.getRole());
    }

    public String generateRefreshToken(Member member) {
        return generateRefreshToken(member.getId(), member.getEmail(), member.getRole());
    }

    public String generateAccessToken(Long memberId, String email, Role role) {
        return generateToken(SecurityConstants.ACCESS_TOKEN_CATEGORY, memberId, email, role,
            jwtProperties.getAccessTokenExpiration());
    }

    public String generateRefreshToken(Long memberId, String email, Role role) {
        return generateToken(SecurityConstants.REFRESH_TOKEN_CATEGORY, memberId, email, role,
            jwtProperties.getRefreshTokenExpiration());
    }

    private String generateToken(String category, Long memberId, String email, Role role, long expirationMs) {
        return jwtUtil.createJwt(category, email, memberId, role, expirationMs);
    }
}
