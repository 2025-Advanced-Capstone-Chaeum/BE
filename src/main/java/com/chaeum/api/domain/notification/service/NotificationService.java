package com.chaeum.api.domain.notification.service;

import com.chaeum.api.domain.notification.dto.response.BeneficiaryNotificationResponse;
import com.chaeum.api.domain.notification.dto.response.CatNotificationResponse;
import com.chaeum.api.domain.notification.dto.response.DonationNotificationResponse;
import com.chaeum.api.domain.notification.dto.response.FriendNotificationResponse;
import com.chaeum.api.domain.notification.dto.response.FundingNotificationResponse;
import com.chaeum.api.domain.notification.dto.response.NotificationResponse;
import com.chaeum.api.domain.notification.dto.response.ReviewNotificationResponse;
import com.chaeum.api.domain.notification.dto.response.RewardNotificationResponse;
import com.chaeum.api.domain.notification.dto.response.TitleNotificationResponse;
import com.chaeum.api.domain.notification.entity.Notification;
import com.chaeum.api.domain.notification.repository.NotificationRepository;
import com.chaeum.api.domain.review.entity.Review;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.pagination.cursorResult.CreatedAtCursorResult;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final LoginMemberProvider loginMemberProvider;

    @Transactional(readOnly = true)
    public CreatedAtCursorResult<NotificationResponse> getMyNotifications(LocalDateTime cursor, int limit) {
        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
        List<Notification> notifications = notificationRepository.findAllByMemberId(memberId);

        List<NotificationResponse> dtos = notifications.stream()
            .filter(notification -> cursor == null || notification.getCreatedAt().isAfter(cursor))
            .sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
            .map(this::mapToDto)
            .toList();

        return CreatedAtCursorResult.of(dtos, cursor, limit);
    }

    private NotificationResponse mapToDto(Notification notification) {
        return switch (notification.getType()) {
            case BENEFICIARY -> BeneficiaryNotificationResponse.toDto(notification);
            case CAT -> CatNotificationResponse.toDto(notification);
            case DONATION -> DonationNotificationResponse.toDto(notification);
            case FRIEND -> FriendNotificationResponse.toDto(notification);
            case FUNDING -> FundingNotificationResponse.toDto(notification);
            case REVIEW -> ReviewNotificationResponse.toDto(notification);
            case REWARD -> RewardNotificationResponse.toDto(notification);
            case TITLE -> TitleNotificationResponse.toDto(notification);
        };
    }
}
