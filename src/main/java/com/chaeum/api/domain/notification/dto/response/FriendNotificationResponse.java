package com.chaeum.api.domain.notification.dto.response;

import com.chaeum.api.domain.notification.entity.Notification;
import com.chaeum.api.domain.notification.entity.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FriendNotificationResponse implements NotificationResponse {

    private NotificationType type;

    private String imageUrl;

    private String content;

    private LocalDateTime createdAt;

    public static FriendNotificationResponse toDto(Notification notification) {
        return FriendNotificationResponse.builder()
            .type(notification.getType())
            .imageUrl(notification.getNotificationImageUrl())
            .content(notification.getContent())
            .createdAt(notification.getCreatedAt())
            .build();
    }
}
