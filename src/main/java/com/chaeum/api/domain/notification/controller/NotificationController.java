package com.chaeum.api.domain.notification.controller;

import com.chaeum.api.domain.notification.dto.response.NotificationResponse;
import com.chaeum.api.domain.notification.service.NotificationService;
import com.chaeum.api.global.pagination.cursorResult.CreatedAtCursorResult;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "알림 관리")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(
        summary = "알림 조회",
        description = """
                    [모든 Role 가능]<br>
                    나의 알림을 조회합니다.<br>
                    현재 알림에 사용되는 행위는 다음과 같습니다.<br>
                    펀딩 등록, 후기 등록, 보상 획득, 수혜자 등록, 기부 참여, 친구 신청, 칭호 획득, 고양이 성장
                    """
    )
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<CreatedAtCursorResult<NotificationResponse>> getMyNotifications(
        @RequestParam(name = "cursor", required = false) LocalDateTime cursor,
        @RequestParam(name = "limit", defaultValue = "8") int limit
    ) {
        return ApiResponse.success(notificationService.getMyNotifications(cursor, limit));
    }
}
