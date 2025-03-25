package com.chaeum.api.domain.paymentRecord.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

    PENDING("PENDING", "결제 대기"),
    COMPLETED("COMPLETED", "결제 완료"),
    FAILED("FAILED", "결제 실패"),
    CANCELED("CANCELED", "결제 취소");

    private final String key;
    private final String description;
}
