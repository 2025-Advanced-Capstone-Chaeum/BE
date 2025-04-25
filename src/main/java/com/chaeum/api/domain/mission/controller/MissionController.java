package com.chaeum.api.domain.mission.controller;

import com.chaeum.api.domain.mission.dto.request.MissionCreateRequest;
import com.chaeum.api.domain.mission.dto.request.MissionUpdateRequest;
import com.chaeum.api.domain.mission.dto.response.MissionResponse;
import com.chaeum.api.domain.mission.entity.MissionType;
import com.chaeum.api.domain.mission.service.MissionService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/mission")
@Tag(name = "Mission", description = "미션 관리")
public class MissionController {

    private final MissionService missionService;

    @Operation(summary = "미션 추가", description = "ADMIN 이상 추가 가능")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ApiResponse<Long> save(
        @Valid @RequestBody MissionCreateRequest missionCreateRequest
    ) {
        Long id = missionService.save(missionCreateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "미션 개별 조회", description = "모든 Role 조회 가능")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<MissionResponse> getMission(
        @RequestParam(name = "missionId") Long missionId
    ) {
        MissionResponse missionResponse = missionService.getMission(missionId);
        return ApiResponse.success(missionResponse);
    }

    @Operation(
        summary = "조건별 미션 조회",
        description = """
            모든 Role 조회 가능<br>
            조건을 하나라도 입력하지 않으면 전체 조회됨<br>
            """
    )
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/condition")
    public ApiResponse<IdCursorResult<MissionResponse>> getMissionsByCondition(
        @RequestParam(name = "missionName", required = false) String missionName,
        @RequestParam(name = "missionType", required = false) MissionType missionType,
        @RequestParam(name = "cursor", required = false) Long cursor,
        @RequestParam(name = "limit", defaultValue = "3") int limit
    ) {
        IdCursorResult<MissionResponse> missions = missionService.getMissionsByCondition(
            missionName, missionType, cursor, limit);
        return ApiResponse.success(missions);
    }

    @Operation(summary = "미션 수정", description = "ADMIN 이상 수정 가능")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("")
    public ApiResponse<Long> updateStatus(
        @RequestParam(name = "missionId") Long missionId,
        @Valid @RequestBody MissionUpdateRequest missionUpdateRequest
    ) {
        Long id = missionService.update(missionId, missionUpdateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "미션 삭제", description = "ADMIN 이상 삭제 가능")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{missionId}")
    public ApiResponse<Long> delete(
        @PathVariable(name = "missionId") Long missionId
    ) {
        Long id = missionService.delete(missionId);
        return ApiResponse.success(id);
    }
}
