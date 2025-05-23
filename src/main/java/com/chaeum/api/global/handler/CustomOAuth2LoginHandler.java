package com.chaeum.api.global.handler;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.global.auth.domain.CustomOAuth2Member;
import com.chaeum.api.global.auth.domain.RefreshToken;
import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.auth.util.TokenProvider;
import com.chaeum.api.global.properties.FrontendProperties;
import com.chaeum.api.global.properties.JwtProperties;
import com.chaeum.api.global.utils.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomOAuth2LoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final MemberService memberService;
    private final FrontendProperties frontendProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        CustomOAuth2Member customUserDetails = (CustomOAuth2Member) authentication.getPrincipal();
        String email = customUserDetails.getEmail();
        Member member = memberService.findByEmail(email);

        String accessToken = tokenProvider.generateAccessToken(member);
        String refreshToken = tokenProvider.generateRefreshToken(member);
        refreshTokenRepository.save(new RefreshToken(member.getId(), refreshToken));

        addCookie(response, SecurityConstants.ACCESS_TOKEN_COOKIE_NAME, accessToken,
            jwtProperties.getAccessTokenExpiration());
        addCookie(response, SecurityConstants.REFRESH_TOKEN_COOKIE_NAME, refreshToken,
            jwtProperties.getRefreshTokenExpiration());

        response.sendRedirect(frontendProperties.getHomeUrl());
    }

    private void addCookie(HttpServletResponse response, String name, String value, long maxAgeMillis) {
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
}
