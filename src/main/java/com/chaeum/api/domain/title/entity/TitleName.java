package com.chaeum.api.domain.title.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TitleName {

    SAESSAK("나눔의 씨앗", "첫 기부 시 획득"),
    SPROUT("희망의 새싹", "5회 기부 시 획득"),
    GROWING_TREE("성장하는 나무", "10회 기부 시 획득"),
    FRUIT_TREE("열매 맺는 나무", "20회 기부 시 획득"),
    FOREST_GUARDIAN("희망의 숲지기", "50회 기부 시 획득"),
    LEGENDARY_TREE("전설의 나무", "100회 기부 시 획득");

    private final String displayName;
    private final String condition;
}
