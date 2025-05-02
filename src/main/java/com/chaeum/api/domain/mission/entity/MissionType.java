package com.chaeum.api.domain.mission.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MissionType {

    ATTENDANCE("ATTENDANCE", "출석 미션"),
    CAT_EXP("CAT_EXP", "고양이 경험치 획득 미션"),
    CAT_INTERACTION("CAT_INTERACTION", "고양이 상호작용 미션"),
    DONATION("DONATION", "기부 미션"),
    ITEM_WEAR("ITEM_WEAR", "아이템 착용 미션");

    private final String key;
    private final String description;
}
