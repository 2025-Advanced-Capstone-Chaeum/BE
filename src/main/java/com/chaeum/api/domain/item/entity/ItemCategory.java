package com.chaeum.api.domain.item.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemCategory {

    DECORATION("DECORATION", "장식"),
    INTERIOR("INTERIOR", "인테리어"),
    INTERACTION("INTERACTION", "상호작용");

    private final String key;
    private final String description;
}
