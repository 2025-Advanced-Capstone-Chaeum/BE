package com.chaeum.api.domain.donation.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DonationStatus {

    ONGOING("ONGOING", "진행 중"),
    COMPLETED("COMPLETED", "완료"),
    FAILED("FAILED", "실패"),
    CANCELED("CANCELED", "취소됨");

    private final String key;
    private final String description;
}
