package com.chaeum.api.global.auth.service;

import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.global.auth.domain.RefreshToken;
import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.properties.JwtProperties;
import com.chaeum.api.global.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    public void reissueAccessToken(String loginId, HttpServletRequest request, HttpServletResponse response) {

        // 1. Refresh Token 추출 및 유효성 검사
        String refreshToken = getRefreshToken(request);

        // 2. Refresh Token에서 사용자 Role 가져오기
        Role role = getRoleFromRefreshToken(refreshToken);

        // 3. 기존 Refresh Token 삭제 (재사용 방지 목적)
        refreshTokenRepository.deleteById(refreshToken);

        // 4. 새로운 Access Token 및 Refresh Token 발급
        String newAccessToken = jwtUtil.createJwt("access", loginId, role, jwtProperties.getAccessTokenExpiration());
        String newRefreshToken = jwtUtil.createJwt("refresh", loginId, role, jwtProperties.getRefreshTokenExpiration());

        // 5. 새로운 Refresh Token을 Redis에 저장
        RefreshToken refreshEntity = new RefreshToken(newRefreshToken, loginId);
        refreshTokenRepository.save(refreshEntity);

        // 6. 새로운 토큰을 응답 헤더 및 쿠키에 저장
        response.addHeader("Authorization", "Bearer " + newAccessToken);
        response.addHeader("Set-Cookie", createCookie("refresh", newRefreshToken).toString());
    }

    // 쿠키에서 Refresh Token을 추출하고 유효성 검사를 수행
    private String getRefreshToken(HttpServletRequest request) {

        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> ChaeumException.from(ErrorCode.TOKEN_NOT_FOUND));

        // 토큰이 유효한지 확인
        if (!jwtUtil.validateToken(refreshToken)) {
            throw ChaeumException.from(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if (jwtUtil.isExpired(refreshToken)) {
            throw ChaeumException.from(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        return refreshToken;
    }

    // Refresh Token에서 사용자 Role 추출
    private Role getRoleFromRefreshToken(String refreshToken) {
        String roleName = String.valueOf(jwtUtil.getRole(refreshToken));
        return Role.valueOf(roleName);
    }

    // 새로운 Refresh Token을 쿠키로 반환
    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .domain(jwtProperties.getCookieDomain())
                .maxAge(jwtProperties.getRefreshTokenExpiration())
                .build();
    }
}
