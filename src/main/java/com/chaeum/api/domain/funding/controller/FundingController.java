package com.chaeum.api.domain.funding.controller;

import com.chaeum.api.domain.funding.dto.request.FundingCreateRequest;
import com.chaeum.api.domain.funding.dto.request.FundingUpdateRequest;
import com.chaeum.api.domain.funding.dto.response.FundingResponse;
import com.chaeum.api.domain.funding.dto.response.RecommendedFundingResponse;
import com.chaeum.api.domain.funding.entity.FundingStatus;
import com.chaeum.api.domain.funding.service.FundingService;
import com.chaeum.api.global.pagination.cursorResult.IdCursorResult;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/funding")
@RequiredArgsConstructor
@Tag(name = "Funding", description = "펀딩 관리")
public class FundingController {

    private final FundingService fundingService;

    @Operation(summary = "펀딩 등록", description = "[RECIPIENT or ADMIN만 가능] 펀딩을 등록합니다.")
    // @PreAuthorize("hasAnyRole('RECIPIENT', 'ADMIN')")
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("")
    public ApiResponse<Long> save(
        @Valid @RequestBody FundingCreateRequest request
    ) {
        Long id = fundingService.save(request);
        return ApiResponse.success(id);
    }

    @Operation(summary = "펀딩 단건 조회", description = "[모든 Role 가능] 펀딩 ID로 펀딩 정보를 조회합니다.")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<FundingResponse> getFunding(
        @RequestParam(name = "fundingId") Long fundingId
    ) {
        FundingResponse fundingResponse = fundingService.getFunding(fundingId);
        return ApiResponse.success(fundingResponse);
    }

    @Operation(
        summary = "조건별 펀딩 조회",
        description = """
            [모든 Role 가능] 펀딩 상태, 펀딩 제목, 커서 기반 페이징으로 펀딩 목록을 조회합니다.<br>
            조건을 모두 생략하면 전체 조회됩니다.
            """
    )
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/condition")
    public ApiResponse<IdCursorResult<FundingResponse>> getFundingsByCondition(
        @RequestParam(name = "status", required = false) FundingStatus status,
        @RequestParam(name = "title", required = false) String title,
        @RequestParam(name = "cursor", required = false) Long cursor,
        @RequestParam(name = "limit", defaultValue = "3") int limit
    ) {
        IdCursorResult<FundingResponse> fundings = fundingService.getFundingsByCondition(status, title, cursor, limit);
        return ApiResponse.success(fundings);
    }

    @Operation(
        summary = "추천순 펀딩 조회",
        description = "[모든 Role 가능] 개인화된 추천 순으로 펀딩 목록을 조회합니다."
    )
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/recommend")
    public ApiResponse<IdCursorResult<RecommendedFundingResponse>> getRecommendedFundings(
        @RequestParam(name = "cursor", required = false) Long cursor,
        @RequestParam(name = "limit", defaultValue = "3") int limit
    ) {
        IdCursorResult<RecommendedFundingResponse> fundings = fundingService.getRecommendedFundings(cursor, limit);
        return ApiResponse.success(fundings);
    }

    @Operation(summary = "펀딩 수정", description = "[ADMIN만 가능] 펀딩 정보를 수정합니다.")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("")
    public ApiResponse<Long> update(
        @RequestParam(name = "fundingId") Long fundingId,
        @Valid @RequestBody FundingUpdateRequest fundingUpdateRequest
    ) {
        Long id = fundingService.update(fundingId, fundingUpdateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "펀딩 삭제", description = "[ADMIN만 가능] 펀딩을 삭제합니다.")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{fundingId}")
    public ApiResponse<Long> delete(
        @PathVariable(name = "fundingId") Long fundingId
    ) {
        Long id = fundingService.delete(fundingId);
        return ApiResponse.success(id);
    }
}
