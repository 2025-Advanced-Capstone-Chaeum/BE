package com.chaeum.api.global.handler;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.domain.CustomOAuth2Member;
import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.properties.JwtProperties;
import com.chaeum.api.global.auth.util.JwtUtil;
import com.chaeum.api.global.auth.domain.RefreshToken;
import com.chaeum.api.global.utils.SecurityConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2LoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${frontend.home-url}")
    private String homeUrl;

    @Value("${spring.jwt.cookie-domain}")
    private String cookieDomain;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {
        CustomOAuth2Member customUserDetails = (CustomOAuth2Member) authentication.getPrincipal();
        String email = customUserDetails.getEmail();
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND));
        Role role = member.getRole();

        String accessToken = createToken(SecurityConstants.ACCESS_TOKEN_CATEGORY, email, role,
            jwtProperties.getAccessTokenExpiration());
        String refreshToken = createToken(SecurityConstants.REFRESH_TOKEN_CATEGORY, email, role,
            jwtProperties.getRefreshTokenExpiration());

        saveRefreshToken(member, refreshToken);

        addTokenCookie(response, SecurityConstants.ACCESS_TOKEN_COOKIE_NAME, accessToken,
            jwtProperties.getAccessTokenExpiration());
        addTokenCookie(response, SecurityConstants.REFRESH_TOKEN_COOKIE_NAME, refreshToken,
            jwtProperties.getRefreshTokenExpiration());

        response.sendRedirect(homeUrl);
    }

    private String createToken(String category, String email, Role role, long expiration) {
        return jwtUtil.createJwt(category, email, role, expiration);
    }

    private void saveRefreshToken(Member member, String refreshTokenValue) {
        RefreshToken refreshToken = new RefreshToken(member.getId(), member.getEmail(), refreshTokenValue);
        refreshTokenRepository.save(refreshToken);
    }

    private void addTokenCookie(HttpServletResponse response, String name, String value, long maxAgeMillis) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
            .maxAge(maxAgeMillis / 1000)
            .path("/")
            .secure(true)
            .httpOnly(true)
            .sameSite("None")
            .domain(cookieDomain)
            .build();

        response.addHeader(SecurityConstants.SET_COOKIE_HEADER, cookie.toString());
    }
}
