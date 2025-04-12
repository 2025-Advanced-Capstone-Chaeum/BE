package com.chaeum.api.domain.title.controller;

import com.chaeum.api.domain.title.dto.response.TitleResponse;
import com.chaeum.api.domain.title.service.TitleService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/title")
@RequiredArgsConstructor
@Tag(name = "Title", description = "칭호 관리")
public class TitleController {

    private final TitleService titleService;

    @Operation(summary = "최근 칭호 조회", description = "[모든 Role 가능]")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<TitleResponse> getLatestTitle() {
        return ApiResponse.success(titleService.getTitle());
    }
}
