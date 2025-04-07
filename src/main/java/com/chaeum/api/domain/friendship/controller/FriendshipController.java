package com.chaeum.api.domain.friendship.controller;

import com.chaeum.api.domain.friendship.dto.request.FriendshipCreateRequest;
import com.chaeum.api.domain.friendship.dto.request.FriendshipUpdateRequest;
import com.chaeum.api.domain.friendship.dto.response.FriendshipResponse;
import com.chaeum.api.domain.friendship.service.FriendshipService;
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
@RequestMapping("/api/v1/friendship")
@RequiredArgsConstructor
@Tag(name = "Friendship", description = "친구 관리")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @Operation(summary = "친구 등록", description = "[모든 Role 가능]")
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("")
    public ApiResponse<Long> save(
        @Valid @RequestBody FriendshipCreateRequest friendshipCreateRequest
    ) {
        Long id = friendshipService.save(friendshipCreateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "친구 개별 조회", description = "[모든 Role 가능]")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<FriendshipResponse> getFriend(
        @RequestParam(name = "friendshipId") Long friendshipId
    ) {
        FriendshipResponse friendshipResponse = friendshipService.getFriend(friendshipId);
        return ApiResponse.success(friendshipResponse);
    }

    @Operation(
        summary = "조건별 친구 조회",
        description = """
            [모든 Role 조회 가능]<br>
            이름을 입력하지 않으면 전체 조회됨<br>
            """
    )
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/condition")
    public ApiResponse<IdCursorResult<FriendshipResponse>> getFriendsByCondition(
        @RequestParam(name = "friendName", required = false) String friendName,
        @RequestParam(name = "cursor", required = false) Long cursor,
        @RequestParam(name = "limit", defaultValue = "6") int limit
    ) {
        IdCursorResult<FriendshipResponse> friendships =
            friendshipService.getFriendshipsByCondition(friendName, cursor, limit);
        return ApiResponse.success(friendships);
    }

    @Operation(summary = "친구 요청 상태 변경", description = "친구 요청 수락, 거절 등")
    @PreAuthorize("hasRole('DONOR')")
    @PatchMapping("")
    public ApiResponse<Long> updateFriendshipStatus(
        @Valid @RequestBody FriendshipUpdateRequest friendshipUpdateRequest
    ) {
        Long id = friendshipService.update(friendshipUpdateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "친구 삭제", description = "[모든 Role 가능]")
    @PreAuthorize("hasRole('DONOR')")
    @DeleteMapping("/{friendshipId}")
    public ApiResponse<Long> delete(
        @PathVariable Long friendshipId
    ) {
        Long id = friendshipService.delete(friendshipId);
        return ApiResponse.success(id);
    }
}
