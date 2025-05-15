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
import java.util.Arrays;
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
        String accessToken = extractAccessToken(request);
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!isValidAccessToken(accessToken, response)) return;

        String email = jwtUtil.getEmail(accessToken);
        CustomMemberDetails customMember = findMemberByEmail(email, response);
        if (customMember == null) return;

        setAuthentication(customMember);
        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String token = extractFromCookie(request);
        return token != null ? token : extractFromHeader(request);
    }

    private String extractFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        return Arrays.stream(cookies)
            .filter(cookie -> "AccessToken".equals(cookie.getName()))
            .map(Cookie::getValue)
            .findFirst()
            .orElse(null);
    }

    private String extractFromHeader(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            ? bearer.substring(7)
            : null;
    }

    private boolean isValidAccessToken(String accessToken, HttpServletResponse response) throws IOException {
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

    private CustomMemberDetails findMemberByEmail(String email, HttpServletResponse response) throws IOException {
        return memberRepository.findByEmail(email)
            .map(CustomMemberDetails::new)
            .orElse(null);
    }

    private void setAuthentication(CustomMemberDetails member) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            member, null, member.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void createErrorAPIResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(), ErrorResponse.error(errorCode));
    }
}
