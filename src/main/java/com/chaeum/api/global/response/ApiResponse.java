package com.chaeum.api.global.response;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.exception.ChaeumException;

@Getter
public class ApiResponse<T> {
    private final Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Metadata metadata;

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

    // 페이지네이션을 포함한 성공 응답 (Page 객체 사용)
    public static <T> ApiResponse<T> success(Page<T> page) {
        return new ApiResponse<>(ErrorCode.REQUEST_OK, page.getContent(), new Metadata(page));
    }

    // 페이지네이션을 포함한 성공 응답 (Slice 객체 사용)
    public static <T> ApiResponse<T> success(Slice<T> slice) {
        return new ApiResponse<>(ErrorCode.REQUEST_OK, slice.getContent(), new Metadata(slice));
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
        this.metadata = new Metadata(results.size());
        this.results = results == null ? List.of() : results;
    }

    private ApiResponse(ErrorCode errorCode, List<T> results, Metadata metadata) {
        this.status = new Status(errorCode);
        this.metadata = metadata;
        this.results = results == null ? List.of() : results;
    }

    private ApiResponse(ErrorCode errorCode) {
        this.status = new Status(errorCode);
        this.metadata = null;
        this.results = List.of();
    }

    @Getter
    @AllArgsConstructor
    private static class Metadata {
        private final int resultCount;  // 결과 개수
        private final int totalPages;   // 전체 페이지 수
        private final int currentPage;  // 현재 페이지 번호
        private final int pageSize;     // 페이지 크기
        private final boolean hasNext;  // 다음 페이지 존재 여부

        public Metadata(int resultCount) {
            this.resultCount = resultCount;
            this.totalPages = 1;
            this.currentPage = 1;
            this.pageSize = resultCount;
            this.hasNext = false;
        }

        public Metadata(Page<?> page) {
            this.resultCount = page.getContent().size();
            this.totalPages = page.getTotalPages();
            this.currentPage = page.getNumber() + 1;
            this.pageSize = page.getSize();
            this.hasNext = page.hasNext();
        }

        public Metadata(Slice<?> slice) {
            this.resultCount = slice.getContent().size();
            this.totalPages = -1;
            this.currentPage = slice.getNumber() + 1;
            this.pageSize = slice.getSize();
            this.hasNext = slice.hasNext();
        }
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