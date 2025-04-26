package com.chaeum.api.domain.notification.dto.response;

import com.chaeum.api.domain.notification.entity.Notification;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BeneficiaryNotificationResponse implements NotificationResponse {

    private String imageUrl;

    private String content;

    private LocalDateTime createdAt;

    public static BeneficiaryNotificationResponse toDto(Notification notification) {
        return BeneficiaryNotificationResponse.builder()
            .imageUrl(notification.getNotificationImageUrl())
            .content(notification.getContent())
            .createdAt(notification.getCreatedAt())
            .build();
    }
}
