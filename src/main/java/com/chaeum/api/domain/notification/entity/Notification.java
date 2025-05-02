package com.chaeum.api.domain.notification.entity;

import com.chaeum.api.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "target_id")
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType type;

    @Column(name = "notification_image_url")
    private String notificationImageUrl;

    @Column(name = "content", nullable = false)
    private String content;

    public static Notification create(
        Long memberId,
        Long targetId,
        NotificationType type,
        String notificationImageUrl,
        String content
    ) {
        return Notification.builder()
            .memberId(memberId)
            .targetId(targetId)
            .type(type)
            .notificationImageUrl(notificationImageUrl)
            .content(content)
            .build();
    }
}
