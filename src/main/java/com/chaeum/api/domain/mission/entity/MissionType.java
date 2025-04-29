package com.chaeum.api.domain.mission.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MissionType {

    ATTENDANCE("ATTENDANCE", "출석 미션"),
    CAT("CAT", "고양이 상호작용 미션"),
    DONATION("DONATION", "기부 미션"),
    FRIEND("FRIEND", "친구 미션"),
    ITEM("ITEM", "아이템 착용 미션");

    private final String key;
    private final String description;
}
