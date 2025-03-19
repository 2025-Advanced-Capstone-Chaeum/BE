package com.chaeum.api.global.filter;

import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.response.ApiResponse;
import com.chaeum.api.global.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 1. 로그아웃 요청인지 확인 (아니면 다음 필터로 넘김)
        if (!isLogoutRequest(httpRequest)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Refresh Token 쿠키에서 추출 (없으면 예외 발생)
        String refreshToken = extractRefreshToken(httpRequest);

        // 3. Refresh Token 유효성 검증 (문제 있으면 예외 응답 반환)
        if (!validateRefreshToken(refreshToken, httpResponse)) {
            return;
        }

        // 4. Redis에서 Refresh Token 삭제
        refreshTokenRepository.deleteById(jwtUtil.getEmail(refreshToken));

        // 5. Refresh Token 쿠키 제거 후 응답 반환
        removeRefreshTokenCookie(httpResponse);
        sendApiResponse(httpResponse, ErrorCode.REQUEST_OK);
    }

    // 로그아웃 확인
    private boolean isLogoutRequest(HttpServletRequest request) {
        return request.getRequestURI().equals("/api/v1/logout") && "POST".equals(request.getMethod());
    }

    // 쿠키에서 Refresh Token 추출
    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw ChaeumException.from(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }
        for (Cookie cookie : request.getCookies()) {
            if ("refresh".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        throw ChaeumException.from(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
    }

    // Refresh Token 검증(유효하면 true)
    private boolean validateRefreshToken(String refreshToken, HttpServletResponse response) throws IOException {
        if (!jwtUtil.validateToken(refreshToken)) {
            sendApiResponse(response, ErrorCode.INVALID_REFRESH_TOKEN);
            return false;
        }
        if (jwtUtil.isExpired(refreshToken)) {
            sendApiResponse(response, ErrorCode.EXPIRED_REFRESH_TOKEN);
            return false;
        }
        if (!"refresh".equals(jwtUtil.getCategory(refreshToken))) {
            sendApiResponse(response, ErrorCode.INVALID_REFRESH_TOKEN);
            return false;
        }
        if (refreshTokenRepository.findById(jwtUtil.getEmail(refreshToken)).isEmpty()) {
            sendApiResponse(response, ErrorCode.INVALID_REFRESH_TOKEN);
            return false;
        }
        return true;
    }

    // Refresh Token 쿠키 삭제
    private void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    // API 응답 반환
    private void sendApiResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(), new ApiResponse<>(errorCode));
    }
}
