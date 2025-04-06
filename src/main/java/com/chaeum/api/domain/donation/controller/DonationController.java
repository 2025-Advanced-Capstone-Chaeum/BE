package com.chaeum.api.domain.donation.controller;

import com.chaeum.api.domain.donation.dto.request.DonationCreateRequest;
import com.chaeum.api.domain.donation.dto.response.DonationCreateResponse;
import com.chaeum.api.domain.donation.service.DonationService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/donation")
@RequiredArgsConstructor
@Tag(name = "Donation", description = "기부 관리")
public class DonationController {

    private final DonationService donationService;

    @Operation(summary = "기부 내역 생성", description = "[DONOR 이상 가능]")
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("")
    public ApiResponse<DonationCreateResponse> save(
        @Valid @RequestBody DonationCreateRequest request
    ) {
        DonationCreateResponse donationCreateResponse = donationService.save(request);
        return ApiResponse.success(donationCreateResponse);
    }

    @Operation(summary = "기부 내역 삭제", description = "[ADMIN 이상 가능]")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{donationId}")
    public ApiResponse<Long> delete(
        @PathVariable(name = "donationId") Long donationId
    ) {
        Long id = donationService.delete(donationId);
        return ApiResponse.success(id);
    }
}
