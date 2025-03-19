package com.chaeum.api.global.auth.controller;

import com.chaeum.api.global.auth.dto.CustomMemberDetails;
import com.chaeum.api.global.auth.service.ReissueService;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reissue")
@Tag(name = "RefreshToken", description = "Access Token 재발급 관리")
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping
    @Operation(summary = "토큰 재발급", description = "access token이 만료되면 새로운 토큰을 발급받습니다.")
    ApiResponse<Void> reissue(
            @AuthenticationPrincipal CustomMemberDetails member,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        reissueService.reissueAccessToken(member.getMember().getEmail(), request, response);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }
}