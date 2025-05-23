package com.chaeum.api.global.filter;

import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.repository.RefreshTokenRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.auth.util.JwtUtil;
import com.chaeum.api.global.utils.ResponseUtil;
import com.chaeum.api.global.utils.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!isLogoutRequest(httpRequest)) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = extractRefreshToken(httpRequest);
        if (!isValidRefreshToken(refreshToken, httpResponse)) return;

        String email = jwtUtil.getEmail(refreshToken);
        Long memberId = memberRepository.findByEmail(email)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND))
            .getId();

        refreshTokenRepository.deleteById(String.valueOf(memberId));

        removeRefreshTokenCookie(httpResponse);
        ResponseUtil.writeSuccess(httpResponse);
    }

    private boolean isLogoutRequest(HttpServletRequest request) {
        return SecurityConstants.LOGOUT_ENDPOINT.equals(request.getRequestURI())
            && SecurityConstants.POST_METHOD.equalsIgnoreCase(request.getMethod());
    }

    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw ChaeumException.from(ErrorCode.NOT_FOUND_REFRESH_TOKEN);
        }

        return Arrays.stream(request.getCookies())
            .filter(cookie -> SecurityConstants.REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
            .map(Cookie::getValue)
            .findFirst()
            .orElseThrow(() -> ChaeumException.from(ErrorCode.NOT_FOUND_REFRESH_TOKEN));
    }

    private boolean isValidRefreshToken(String token, HttpServletResponse response) throws IOException {
        if (!jwtUtil.validateToken(token)) {
            ResponseUtil.writeError(response, ErrorCode.INVALID_REFRESH_TOKEN);
            return false;
        }
        if (jwtUtil.isExpired(token)) {
            ResponseUtil.writeError(response, ErrorCode.EXPIRED_REFRESH_TOKEN);
            return false;
        }
        if (!SecurityConstants.REFRESH_TOKEN_CATEGORY.equals(jwtUtil.getCategory(token))) {
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
        Cookie cookie = new Cookie(SecurityConstants.REFRESH_TOKEN_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
