package com.chaeum.api.domain.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    BENEFICIARY("Beneficiary", "수혜자 등록 알림"),
    DONATION("Donation", "기부 알림"),
    FRIEND("Friend", "친구 신청/거절 알림"),
    FUNDING("Funding", "펀딩 관련 알림"),
    REVIEW("Review", "후기 등록 알림"),
    REWARD("Reward", "보상 획득 알림"),
    TITLE("Title", "칭호 획득 알림");

    private final String key;
    private final String description;
}
