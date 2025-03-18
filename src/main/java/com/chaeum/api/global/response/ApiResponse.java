package com.chaeum.api.global.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.exception.ChaeumException;

@Getter
public class ApiResponse<T> {
    private final Status status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<T> results;

    // 단일 데이터에 대한 성공 응답
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ErrorCode.REQUEST_OK, data == null ? List.of() : List.of(data));
    }

    // 리스트 형태의 데이터에 대한 성공 응답
    public static <T> ApiResponse<T> success(List<T> results) {
        return new ApiResponse<>(ErrorCode.REQUEST_OK, results == null ? List.of() : results);
    }

    // 에러 응답 (ErrorCode 사용)
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode);
    }

    // 에러 응답 (ChaeumException 사용)
    public static <T> ApiResponse<T> error(ChaeumException exception) {
        return new ApiResponse<>(exception.getErrorCode());
    }

    private ApiResponse(ErrorCode errorCode, List<T> results) {
        this.status = new Status(errorCode);
        this.results = results == null ? List.of() : results;
    }

    public ApiResponse(ErrorCode errorCode) {
        this.status = new Status(errorCode);
        this.results = List.of();
    }

    @Getter
    private static class Status {
        private final int code;       // HTTP 상태 코드
        private final String message; // 상태 메시지

        public Status(ErrorCode errorCode) {
            this.code = errorCode.getStatus().value();
            this.message = errorCode.getMessage();
        }
    }
}
