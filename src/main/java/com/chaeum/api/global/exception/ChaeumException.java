package com.chaeum.api.global.exception;

import lombok.Getter;

@Getter
public class ChaeumException extends RuntimeException {
    private final ErrorCode errorCode;
    private String message;

    // 기본 생성자 (에러 코드만 전달)
    private ChaeumException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 커스텀 메시지를 포함하는 생성자
    private ChaeumException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    // 에러 코드만으로 예외 생성
    public static ChaeumException from(ErrorCode errorCode) {
        return new ChaeumException(errorCode);
    }

    // 에러 코드와 커스텀 메시지로 예외 생성
    public static ChaeumException from(ErrorCode errorCode, String message) {
        return new ChaeumException(errorCode, message);
    }
}