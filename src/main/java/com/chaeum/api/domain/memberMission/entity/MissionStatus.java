package com.chaeum.api.domain.memberMission.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MissionStatus {

    PENDING("PENDING", "대기 중"),
    IN_PROGRESS("IN_PROGRESS", "진행 중"),
    COMPLETED("COMPLETED", "완료됨"),
    FAILED("FAILED", "실패함"),
    CANCELED("CANCELED", "취소됨");

    private final String key;
    private final String description;
}
