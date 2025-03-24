package com.chaeum.api.domain.payment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {

    PAYCO("PAYCO", "페이코"),
    TOSS_PAY("TOSS_PAY", "토스페이"),
    KAKAO_PAY("KAKAO_PAY", "카카오페이");

    private final String key;
    private final String description;
}
