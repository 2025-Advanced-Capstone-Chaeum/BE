package com.chaeum.api.domain.review.controller;

import com.chaeum.api.domain.review.dto.request.ReviewCreateRequest;
import com.chaeum.api.domain.review.dto.request.ReviewUpdateRequest;
import com.chaeum.api.domain.review.dto.response.ReviewResponse;
import com.chaeum.api.domain.review.dto.response.ReviewSummaryResponse;
import com.chaeum.api.domain.review.service.ReviewService;
import com.chaeum.api.global.pagination.cursorResult.CreatedAtCursorResult;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Tag(name = "Review", description = "펀딩 후기 관리")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "펀딩 후기 등록", description = "[RECIPIENT 이상 가능]")
    @PreAuthorize("hasRole('RECIPIENT')")
    @PostMapping("")
    public ApiResponse<Long> save(
        @RequestParam(name = "fundingId") Long fundingId,
        @Valid @RequestBody ReviewCreateRequest reviewCreateRequest
    ) {
        Long id = reviewService.save(fundingId, reviewCreateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "펀딩 후기 상세 조회", description = "[모든 Role 가능] 펀딩 ID로 후기 상세 정보를 조회합니다.")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/details")
    public ApiResponse<ReviewResponse> getReviewDetails(
        @RequestParam(name = "fundingId") Long fundingId
    ) {
        ReviewResponse reviewResponse = reviewService.getReviewDetails(fundingId);
        return ApiResponse.success(reviewResponse);
    }

    @Operation(summary = "펀딩 후기 목록 조회", description = "[모든 Role 가능] 후기 최신순으로 조회합니다.")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/list")
    public ApiResponse<CreatedAtCursorResult<ReviewSummaryResponse>> getReviews(
        @RequestParam(name = "cursor", required = false) LocalDateTime cursor,
        @RequestParam(name = "limit", defaultValue = "8") int limit
    ) {
        CreatedAtCursorResult<ReviewSummaryResponse> reviews = reviewService.getReviews(cursor, limit);
        return ApiResponse.success(reviews);
    }

    @Operation(summary = "펀딩 후기 변경", description = "[RECIPIENT 이상 가능]")
    @PreAuthorize("hasRole('RECIPIENT')")
    @PatchMapping("")
    public ApiResponse<Long> update(
        @RequestParam(name = "reviewId") Long fundingId,
        @Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest
    ) {
        Long id = reviewService.update(fundingId, reviewUpdateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "펀딩 후기 삭제", description = "[ADMIN 이상 가능]")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{reviewId}")
    public ApiResponse<Long> delete(
        @PathVariable(name = "reviewId") Long reviewId
    ) {
        Long id = reviewService.delete(reviewId);
        return ApiResponse.success(id);
    }
}
