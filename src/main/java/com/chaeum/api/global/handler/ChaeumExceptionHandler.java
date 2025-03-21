package com.chaeum.api.global.handler;

import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ChaeumExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger("ErrorLogger");
    private static final String LOG_FORMAT_INFO = "\n[🔵INFO] - ({} {})\n(errorCode: {}, className: {})\n{}\n {}: {}";
    private static final String LOG_FORMAT_WARN = "\n[🟠WARN] - ({} {})\n(id: {}, role: {})";
    private static final String LOG_FORMAT_ERROR = "\n[🔴ERROR] - ({} {})\n(id: {}, role: {})";

    @ExceptionHandler(ChaeumException.class)
    public ApiResponse handle(ChaeumException exception, HttpServletRequest request) {
        logInfo(exception, request);
        return new ApiResponse<>(exception.getErrorCode());
    }

    private void logInfo(ChaeumException e, HttpServletRequest request) {
        log.info(LOG_FORMAT_INFO, request.getMethod(), request.getRequestURI(), e.getErrorCode(), e.getClass().getName(), e.getMessage());
    }

    private void logWarn(ChaeumException e, HttpServletRequest request) {
        log.warn(LOG_FORMAT_WARN, request.getMethod(), request.getRequestURI(), e);
    }

    private void logError(Exception e, HttpServletRequest request) {
        log.error(LOG_FORMAT_ERROR, request.getMethod(), request.getRequestURI(), e);
    }
}
