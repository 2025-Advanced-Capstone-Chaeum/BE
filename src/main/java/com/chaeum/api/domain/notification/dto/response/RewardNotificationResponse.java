package com.chaeum.api.domain.notification.dto.response;

import com.chaeum.api.domain.notification.entity.Notification;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RewardNotificationResponse implements NotificationResponse {

    private String imageUrl;

    private String content;

    private LocalDateTime createdAt;

    public static RewardNotificationResponse toDto(Notification notification) {
        return RewardNotificationResponse.builder()
            .imageUrl(notification.getNotificationImageUrl())
            .content(notification.getContent())
            .createdAt(notification.getCreatedAt())
            .build();
    }
}
