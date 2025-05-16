package com.chaeum.api.global.auth.service;

import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.global.auth.domain.RefreshToken;
import com.chaeum.api.global.auth.dto.CustomMemberDetails;
import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.properties.JwtProperties;
import com.chaeum.api.global.auth.util.JwtUtil;
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

    public void reissueAccessToken(CustomMemberDetails member, HttpServletRequest request, HttpServletResponse response) {
        // 1. 쿠키에서 Refresh Token 추출 및 유효성 검사
        String refreshToken = extractValidRefreshToken(request);

        // 2. 사용자 정보 추출
        Long memberId = member.member().getId();
        String email = jwtUtil.getEmail(refreshToken);
        Role role = Role.valueOf(String.valueOf(jwtUtil.getRole(refreshToken)));

        // 3. 기존 Refresh Token 삭제
        refreshTokenRepository.deleteById(String.valueOf(memberId));

        // 4. 새로운 토큰 발급
        String newAccessToken = jwtUtil.createJwt("access", email, role, jwtProperties.getAccessTokenExpiration());
        String newRefreshToken = jwtUtil.createJwt("refresh", email, role, jwtProperties.getRefreshTokenExpiration());

        // 5. Redis에 새로운 Refresh Token 저장
        RefreshToken refreshEntity = new RefreshToken(memberId, email, newRefreshToken);
        refreshTokenRepository.save(refreshEntity);

        // 6. 응답에 토큰 세팅
        response.addHeader("Authorization", "Bearer " + newAccessToken);
        response.addHeader("Set-Cookie", createRefreshCookie(newRefreshToken).toString());
    }

    private String extractValidRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> ChaeumException.from(ErrorCode.TOKEN_NOT_FOUND));

        if (!jwtUtil.validateToken(refreshToken)) {
            throw ChaeumException.from(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if (jwtUtil.isExpired(refreshToken)) {
            throw ChaeumException.from(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        return refreshToken;
    }

    private ResponseCookie createRefreshCookie(String value) {
        return ResponseCookie.from("refresh", value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .domain(jwtProperties.getCookieDomain())
                .maxAge(jwtProperties.getRefreshTokenExpiration())
                .build();
    }
}
