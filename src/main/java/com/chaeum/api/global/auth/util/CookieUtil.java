package com.chaeum.api.global.auth.util;

import com.chaeum.api.global.utils.SecurityConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static Optional<String> getValue(HttpServletRequest request, String name) {
        return Optional.ofNullable(request.getCookies())
            .flatMap(cookies -> Arrays.stream(cookies)
                .filter(c -> c.getName().equals(name))
                .map(Cookie::getValue)
                .findFirst());
    }

    public static void addCookie(HttpServletResponse response, String name, String value, long maxAgeMillis,
        String domain) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
            .maxAge(maxAgeMillis / 1000)
            .path("/")
            .secure(true)
            .httpOnly(true)
            .sameSite("None")
            .domain(domain)
            .build();

        response.addHeader(SecurityConstants.SET_COOKIE_HEADER, cookie.toString());
    }

    public static void deleteCookie(HttpServletResponse response, String name, String domain) {
        ResponseCookie cookie = ResponseCookie.from(name, null)
            .maxAge(0)
            .path("/")
            .secure(true)
            .httpOnly(true)
            .sameSite("None")
            .domain(domain)
            .build();

        response.addHeader(SecurityConstants.SET_COOKIE_HEADER, cookie.toString());
    }
}
