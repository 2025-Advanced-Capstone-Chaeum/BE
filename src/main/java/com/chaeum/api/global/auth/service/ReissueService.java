package com.chaeum.api.global.auth.service;

import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.global.auth.domain.RefreshToken;
import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.auth.util.CookieUtil;
import com.chaeum.api.global.auth.util.TokenProvider;
import com.chaeum.api.global.auth.util.TokenValidator;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.properties.JwtProperties;
import com.chaeum.api.global.utils.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final TokenValidator tokenValidator;
    private final RefreshTokenRepository refreshTokenRepository;

    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshTokenFromCookie(request);
        tokenValidator.validateRefreshToken(refreshToken);

        String email = tokenValidator.extractEmail(refreshToken);
        Role role = tokenValidator.extractRole(refreshToken);
        Long memberId = tokenValidator.extractMemberId(refreshToken);

        refreshTokenRepository.deleteById(String.valueOf(memberId));

        String newAccessToken = tokenProvider.generateAccessToken(memberId, email, role);
        String newRefreshToken = tokenProvider.generateRefreshToken(memberId, email, role);

        refreshTokenRepository.save(new RefreshToken(memberId, newRefreshToken));

        CookieUtil.addCookie(response, SecurityConstants.ACCESS_TOKEN_COOKIE_NAME, newAccessToken,
            jwtProperties.getAccessTokenExpiration(), jwtProperties.getCookieDomain());
        CookieUtil.addCookie(response, SecurityConstants.REFRESH_TOKEN_COOKIE_NAME, newRefreshToken,
            jwtProperties.getRefreshTokenExpiration(), jwtProperties.getCookieDomain());
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        return CookieUtil.getValue(request, SecurityConstants.REFRESH_TOKEN_COOKIE_NAME)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.TOKEN_NOT_FOUND));
    }


    public void deleteById(Long memberId) {
        refreshTokenRepository.deleteById(String.valueOf(memberId));
    }
}
