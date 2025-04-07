package com.chaeum.api.domain.title.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TitleName {

    DONATION_ANGEL("DONATION_ANGEL", "기부 천사"),
    EARLY_BIRD("EARLY_BIRD", "얼리버드"),
    CHALLENGER("CHALLENGER", "도전가"),
    FRIENDSHIP_MASTER("FRIENDSHIP_MASTER", "친구왕"),
    SUPER_SUPPORTER("SUPER_SUPPORTER", "슈퍼 서포터"),
    MONTHLY_TOP_DONOR("MONTHLY_TOP_DONOR", "이달의 기부왕");

    private final String key;
    private final String description;
}
