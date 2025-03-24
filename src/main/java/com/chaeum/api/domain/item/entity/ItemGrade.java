package com.chaeum.api.domain.item.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemGrade {

    BRONZE("BRONZE", "브론즈 등급", 0.5),
    SILVER("SILVER", "실버 등급", 0.3),
    GOLD("GOLD", "골드 등급", 0.1),
    PLATINUM("PLATINUM", "플래티넘 등급", 0.05),
    DIAMOND("DIAMOND", "다이아몬드 등급", 0.01);

    private final String key;
    private final String description;
    private final double probability;
}
