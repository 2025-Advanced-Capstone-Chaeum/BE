package com.chaeum.api.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class InternalApiKeyFilter extends OncePerRequestFilter {

    @Value("${internal.api.key}")
    private String internalApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        if ("PATCH".equals(request.getMethod())
            && request.getRequestURI().equals("/api/v1/member/beneficiary")) {

            String apiKey = request.getHeader("X-Internal-key");
            if (apiKey == null || !apiKey.equals(internalApiKey)) {
                response.sendError(HttpStatus.FORBIDDEN.value(),
                    "Forbidden: invalid API key");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
