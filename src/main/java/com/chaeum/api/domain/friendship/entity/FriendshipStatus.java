package com.chaeum.api.domain.friendship.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendshipStatus {

    PENDING("PENDING", "친구 요청 대기") {
        @Override
        public void apply(Friendship friendship) {
            friendship.changeStatus(this);
        }
    },
    ACCEPTED("ACCEPTED", "친구 수락됨") {
        @Override
        public void apply(Friendship friendship) {
            friendship.accept();
        }
    },
    REJECTED("REJECTED", "친구 요청 거절") {
        @Override
        public void apply(Friendship friendship) {
            friendship.reject();
        }
    },
    BLOCKED("BLOCKED", "차단됨") {
        @Override
        public void apply(Friendship friendship) {
            friendship.changeStatus(this);
        }
    },
    CANCELED("CANCELED", "요청 취소됨") {
        @Override
        public void apply(Friendship friendship) {
            friendship.changeStatus(this);
        }
    };

    private final String key;
    private final String description;

    public abstract void apply(Friendship friendship);
}