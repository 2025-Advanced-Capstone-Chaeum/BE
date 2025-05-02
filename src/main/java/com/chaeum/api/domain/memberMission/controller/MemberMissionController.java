package com.chaeum.api.domain.memberMission.controller;

import com.chaeum.api.domain.memberMission.dto.response.MemberMissionResponse;
import com.chaeum.api.domain.memberMission.service.MemberMissionService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member-mission")
@Tag(name = "MemberMission", description = "회원별 미션 관리")
public class MemberMissionController {

    private final MemberMissionService memberMissionService;

    @Operation(
        summary = "내 미션 조회",
        description = """
            모든 Role 조회 가능<br>
            회원별 미션은 5개를 제공하며, 매일 자정에 초기화됩니다.<br>
            """)
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<List<MemberMissionResponse>> getMemberMission() {
        List<MemberMissionResponse> missions = memberMissionService.getMemberMissions();
        return ApiResponse.success(missions);
    }
}
