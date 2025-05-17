package com.chaeum.api.global.auth.controller;

import com.chaeum.api.global.auth.service.ReissueService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reissue")
@Tag(name = "RefreshToken", description = "Access Token 재발급 관리")
public class ReissueController {

    private final ReissueService reissueService;

    @Operation(
        summary = "토큰 재발급",
        description = """
            [모든 Role 사용 가능] Access Token이 만료되면 새로운 토큰을 발급받습니다.
            기존의 Refresh Token은 삭제되며, 새로운 Access Token 및 Refresh Token을 발급받습니다.
            두 토큰은 쿠키에 
            """
    )
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("")
    ApiResponse<Void> reissue(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        reissueService.reissueAccessToken(request, response);
        return ApiResponse.success();
    }
}
