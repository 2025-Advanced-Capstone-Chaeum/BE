package com.chaeum.api.global.filter;

import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.dto.CustomMemberDetails;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.auth.util.JwtUtil;
import com.chaeum.api.global.utils.ResponseUtil;
import com.chaeum.api.global.utils.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

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

        String accessToken = extractAccessToken(request);

        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!validateAccessTokenOrRespond(accessToken, response)) {
            return;
        }

        String email = jwtUtil.getEmail(accessToken);
        CustomMemberDetails memberDetails = memberRepository.findByEmail(email)
            .map(CustomMemberDetails::new)
            .orElse(null);

        if (memberDetails == null) {
            ResponseUtil.writeError(response, ErrorCode.MEMBER_NOT_FOUND);
            return;
        }

        setAuthentication(memberDetails);
        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        return extractFromCookie(request).orElseGet(() -> extractFromHeader(request));
    }

    private Optional<String> extractFromCookie(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
            .flatMap(cookies -> Arrays.stream(cookies)
                .filter(cookie -> SecurityConstants.ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst());
    }

    private String extractFromHeader(HttpServletRequest request) {
        String bearer = request.getHeader(SecurityConstants.AUTH_HEADER);
        return (StringUtils.hasText(bearer) && bearer.startsWith(SecurityConstants.BEARER_PREFIX))
            ? bearer.substring(SecurityConstants.BEARER_PREFIX.length())
            : null;
    }

    private boolean validateAccessTokenOrRespond(String token, HttpServletResponse response) throws IOException {
        if (!jwtUtil.validateToken(token)) {
            ResponseUtil.writeError(response, ErrorCode.INVALID_AUTH_TOKEN);
            return false;
        }
        if (jwtUtil.isExpired(token)) {
            ResponseUtil.writeError(response, ErrorCode.EXPIRED_AUTH_TOKEN);
            return false;
        }
        if (!SecurityConstants.ACCESS_TOKEN_CATEGORY.equals(jwtUtil.getCategory(token))) {
            ResponseUtil.writeError(response, ErrorCode.INVALID_AUTH_TOKEN);
            return false;
        }
        return true;
    }

    private void setAuthentication(CustomMemberDetails memberDetails) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            memberDetails, null, memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
