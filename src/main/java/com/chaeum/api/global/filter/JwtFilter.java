package com.chaeum.api.global.filter;

import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.dto.CustomMemberDetails;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.response.ErrorResponse;
import com.chaeum.api.global.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. 헤더에서 Access Token 추출
        String accessToken = resolveTokenFromCookie(request) == null ? getTokenFromRequestBearer(request) : resolveTokenFromCookie(request);
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!isValidAccessToken(accessToken, response)) return;

        // 2. 토큰 검증
        if (!validateAndParseToken(accessToken, response)) {
            return;
        }

        // 3. 토큰에서 유저 이메일 정보 추출
        String email = jwtUtil.getEmail(accessToken);

        // 4. 이메일 기반 사용자 조회
        CustomMemberDetails customMemberDetails = loadUserByEmail(email, response);
        if (customMemberDetails == null) {
            return;
        }

        // 5. Spring Security Context에 사용자 등록
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customMemberDetails,
                null,
                customMemberDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String token = extractFromCookie(request);
        return token != null ? token : extractFromHeader(request);
    }

    private String extractFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if ("AccessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    // Swgger 테스트 시에 사용합니다.
    private String getTokenFromRequestBearer(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 부분 제거
        }
        return null;
    }

    // 토큰 검증 및 파싱
    private boolean validateAndParseToken(String accessToken, HttpServletResponse response) throws IOException {
        if (!jwtUtil.validateToken(accessToken)) {
            createErrorAPIResponse(response, ErrorCode.INVALID_AUTH_TOKEN);
            return false;
        }
        if (jwtUtil.isExpired(accessToken)) {
            createErrorAPIResponse(response, ErrorCode.EXPIRED_AUTH_TOKEN);
            return false;
        }
        if (!"access".equals(jwtUtil.getCategory(accessToken))) {
            createErrorAPIResponse(response, ErrorCode.INVALID_AUTH_TOKEN);
            return false;
        }
        return true;
    }

    // 이메일로 사용자 조회
    private CustomMemberDetails loadUserByEmail(String email, HttpServletResponse response) throws IOException {
        return memberRepository.findByEmail(email)
                .map(CustomMemberDetails::new)
                .orElse(null);
    }

    // API 응답 생성
    private void createErrorAPIResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(), ErrorResponse.error(errorCode));
    }
}
