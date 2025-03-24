package com.chaeum.api.domain.payment.entity;

import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {

    KAKAO_PAY("KAKAO_PAY", "카카오페이"),
    PAYCO("PAYCO", "페이코"),
    TOSS_PAY("TOSS_PAY", "토스페이");

    private final String key;
    private final String description;

    public static PaymentMethod create(String method) {
        return Arrays.stream(PaymentMethod.values())
                .filter(pm -> pm.name().equalsIgnoreCase(method))
                .findFirst()
                .orElseThrow(() -> ChaeumException.from(ErrorCode.INVALID_PAYMENT_METHOD));
    }
}
