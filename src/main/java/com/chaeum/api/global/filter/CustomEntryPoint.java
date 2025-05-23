package com.chaeum.api.global.filter;

import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException e
    ) throws IOException {
        ResponseUtil.writeError(response, ErrorCode.MEMBER_NOT_AUTHENTICATED);
    }
}
