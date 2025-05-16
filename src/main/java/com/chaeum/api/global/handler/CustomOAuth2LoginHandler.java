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
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

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

        // 1. 로그인한 사용자 정보 가져오기
        CustomOAuth2Member customUserDetails = (CustomOAuth2Member) authentication.getPrincipal();
        String email = customUserDetails.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND));
        Long memberId = member.getId();

        // 2. 사용자 Role 가져오기
        Role memberRole = member.getRole();

        // 3. Access Token & Refresh Token 생성
        String accessToken = jwtUtil.createJwt("access", email, memberRole, jwtProperties.getAccessTokenExpiration());
        String refreshTokenValue = jwtUtil.createJwt("refresh", email, memberRole,
            jwtProperties.getRefreshTokenExpiration());

        // 4. Redis에 Refresh Token 저장
        RefreshToken refreshToken = new RefreshToken(memberId, email, refreshTokenValue);
        refreshTokenRepository.save(refreshToken);

        // 5. 쿠키에 토큰 추가
        addCookie(response, "AccessToken", accessToken, jwtProperties.getAccessTokenExpiration());
        addCookie(response, "RefreshToken", refreshTokenValue, jwtProperties.getRefreshTokenExpiration());

        // 6. 클라이언트 리다이렉트
        response.sendRedirect(homeUrl);
    }

    private void addCookie(HttpServletResponse response, String name, String value, long maxAgeMillis) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
            .maxAge(maxAgeMillis / 1000)
            .path("/")
            .secure(true)
            .httpOnly(true)
            .sameSite("None")
            .domain(cookieDomain)
            .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
