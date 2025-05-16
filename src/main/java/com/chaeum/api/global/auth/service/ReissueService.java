package com.chaeum.api.global.auth.service;

import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.global.auth.domain.RefreshToken;
import com.chaeum.api.global.auth.dto.CustomMemberDetails;
import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.properties.JwtProperties;
import com.chaeum.api.global.auth.util.JwtUtil;
import com.chaeum.api.global.utils.SecurityConstants;
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
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public void reissueAccessToken(
        CustomMemberDetails member,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String refreshToken = extractValidRefreshToken(request);

        Long memberId = member.member().getId();
        String email = jwtUtil.getEmail(refreshToken);
        Role role = Role.valueOf(String.valueOf(jwtUtil.getRole(refreshToken)));

        refreshTokenRepository.deleteById(String.valueOf(memberId));

        String newAccessToken = jwtUtil.createJwt(SecurityConstants.ACCESS_TOKEN_CATEGORY, email, role,
            jwtProperties.getAccessTokenExpiration());
        String newRefreshToken = jwtUtil.createJwt(SecurityConstants.REFRESH_TOKEN_CATEGORY, email, role,
            jwtProperties.getRefreshTokenExpiration());

        refreshTokenRepository.save(new RefreshToken(memberId, email, newRefreshToken));

        response.addHeader(SecurityConstants.AUTH_HEADER, SecurityConstants.BEARER_PREFIX + newAccessToken);
        response.addHeader(SecurityConstants.SET_COOKIE_HEADER, createRefreshCookie(newRefreshToken).toString());
    }

    private String extractValidRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);

        String refreshToken = Arrays.stream(cookies)
            .filter(cookie -> SecurityConstants.REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
            .map(Cookie::getValue)
            .findFirst()
            .orElseThrow(() -> ChaeumException.from(ErrorCode.TOKEN_NOT_FOUND));

        if (!jwtUtil.validateToken(refreshToken)) {
            throw ChaeumException.from(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        if (jwtUtil.isExpired(refreshToken)) {
            throw ChaeumException.from(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        if (!SecurityConstants.REFRESH_TOKEN_CATEGORY.equals(jwtUtil.getCategory(refreshToken))) {
            throw ChaeumException.from(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        return refreshToken;
    }

    private ResponseCookie createRefreshCookie(String value) {
        return ResponseCookie.from(SecurityConstants.REFRESH_TOKEN_COOKIE_NAME, value)
            .path("/")
            .sameSite("None")
            .httpOnly(true)
            .secure(true)
            .domain(jwtProperties.getCookieDomain())
            .maxAge(jwtProperties.getRefreshTokenExpiration())
            .build();
    }
}
