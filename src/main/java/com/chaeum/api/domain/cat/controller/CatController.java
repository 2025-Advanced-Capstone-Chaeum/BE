package com.chaeum.api.domain.cat.controller;

import com.chaeum.api.domain.cat.dto.response.CatInformationResponse;
import com.chaeum.api.domain.cat.service.CatService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cat")
@RequiredArgsConstructor
@Tag(name = "Cat", description = "고양이 관리")
public class CatController {

    private final CatService catService;

    @Operation(summary = "내 고양이 정보 조회", description = "[모든 Role 사용 가능]")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<CatInformationResponse> getMyCatInformation() {
        CatInformationResponse catInformationResponse = catService.getMyCatInformation();
        return ApiResponse.success(catInformationResponse);
    }
}
