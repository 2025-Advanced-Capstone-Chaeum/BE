package com.chaeum.api.domain.funding.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FundingStatus {

    ONGOING("ONGOING", "진행 중"),
    COMPLETED("COMPLETED", "완료"),
    FAILED("FAILED", "실패");

    private final String key;
    private final String description;
}
