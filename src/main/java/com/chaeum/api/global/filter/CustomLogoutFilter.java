package com.chaeum.api.global.filter;

import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.auth.util.JwtUtil;
import com.chaeum.api.global.utils.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private static final String REFRESH_COOKIE_NAME = "refresh";

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!isLogoutRequest(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        String refreshToken = extractRefreshToken(httpRequest);
        if (!validateRefreshToken(refreshToken, httpResponse)) {
            return;
        }

        refreshTokenRepository.deleteById(jwtUtil.getEmail(refreshToken));
        removeRefreshTokenCookie(httpResponse);
        ResponseUtil.writeSuccess(httpResponse);
    }

    private boolean isLogoutRequest(HttpServletRequest request) {
        return "/api/v1/logout".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod());
    }

    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw ChaeumException.from(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }
        for (Cookie cookie : request.getCookies()) {
            if (REFRESH_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        throw ChaeumException.from(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
    }

    private boolean validateRefreshToken(String token, HttpServletResponse response) throws IOException {
        if (!jwtUtil.validateToken(token)) {
            ResponseUtil.writeError(response, ErrorCode.INVALID_REFRESH_TOKEN);
            return false;
        }

        if (jwtUtil.isExpired(token)) {
            ResponseUtil.writeError(response, ErrorCode.EXPIRED_REFRESH_TOKEN);
            return false;
        }

        if (!"refresh".equals(jwtUtil.getCategory(token))) {
            ResponseUtil.writeError(response, ErrorCode.INVALID_REFRESH_TOKEN);
            return false;
        }

        if (refreshTokenRepository.findById(jwtUtil.getEmail(token)).isEmpty()) {
            ResponseUtil.writeError(response, ErrorCode.INVALID_REFRESH_TOKEN);
            return false;
        }

        return true;
    }

    private void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
