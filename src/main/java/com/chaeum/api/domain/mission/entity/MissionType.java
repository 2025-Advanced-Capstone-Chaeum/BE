package com.chaeum.api.domain.mission.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MissionType {

    CHECK_IN("CHECK_IN", "출석 미션"),
    DONATION("DONATION", "기부 미션"),
    REFERRAL("REFERRAL", "친구 초대 미션"),
    CUSTOM("CUSTOM", "커스텀 미션");

    private final String key;
    private final String description;
}
