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

    private static final String OCR_HEADER_NAME = "X-Internal-key";
    private static final String ERROR_MESSAGE = "Forbidden: invalid API key";
    private static final String OCR_API_PATH = "/api/v1/member/beneficiary";

    @Value("${internal.api.key}")
    private String internalApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        if ("PATCH".equals(request.getMethod())
            && request.getRequestURI().equals(OCR_API_PATH)) {

            String apiKey = request.getHeader(OCR_HEADER_NAME);
            if (apiKey == null || !apiKey.equals(internalApiKey)) {
                response.sendError(HttpStatus.FORBIDDEN.value(), ERROR_MESSAGE);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
