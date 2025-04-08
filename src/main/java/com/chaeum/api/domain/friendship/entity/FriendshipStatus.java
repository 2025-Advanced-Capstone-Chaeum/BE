package com.chaeum.api.domain.friendship.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendshipStatus {

    PENDING("PENDING", "친구 요청 대기"),
    ACCEPTED("ACCEPTED", "친구 수락됨"),
    REJECTED("REJECTED", "친구 요청 거절"),
    BLOCKED("BLOCKED", "차단됨"),
    CANCELED("CANCELED", "요청 취소됨");

    private final String key;
    private final String description;
}
