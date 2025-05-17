package com.chaeum.api.global.auth.service;

import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.global.auth.domain.RefreshToken;
import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
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
    private final LoginMemberProvider loginMemberProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public void reissueAccessToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String refreshToken = extractValidRefreshToken(request);

        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
        String email = jwtUtil.getEmail(refreshToken);
        Role role = Role.valueOf(String.valueOf(jwtUtil.getRole(refreshToken)));

        refreshTokenRepository.deleteById(String.valueOf(memberId));

        String newAccessToken = createToken(SecurityConstants.ACCESS_TOKEN_CATEGORY, email, role,
            jwtProperties.getAccessTokenExpiration());
        String newRefreshToken = createToken(SecurityConstants.REFRESH_TOKEN_CATEGORY, email, role,
            jwtProperties.getRefreshTokenExpiration());

        refreshTokenRepository.save(new RefreshToken(memberId, newRefreshToken));

        addTokenCookie(response, SecurityConstants.ACCESS_TOKEN_COOKIE_NAME, newAccessToken,
            jwtProperties.getAccessTokenExpiration());
        addTokenCookie(response, SecurityConstants.REFRESH_TOKEN_COOKIE_NAME, newRefreshToken,
            jwtProperties.getRefreshTokenExpiration());
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

    private void addTokenCookie(HttpServletResponse response, String name, String value, long maxAgeMillis) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
            .maxAge(maxAgeMillis / 1000)
            .path("/")
            .secure(true)
            .httpOnly(true)
            .sameSite("None")
            .domain(jwtProperties.getCookieDomain())
            .build();

        response.addHeader(SecurityConstants.SET_COOKIE_HEADER, cookie.toString());
    }

    private String createToken(String category, String email, Role role, long expirationMillis) {
        return jwtUtil.createJwt(category, email, role, expirationMillis);
    }
}
