package com.chaeum.api.domain.payment.dto.response;

import com.chaeum.api.domain.payment.entity.Payment;
import com.chaeum.api.domain.payment.entity.PaymentMethod;
import com.chaeum.api.domain.payment.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PaymentResponse {
    private Long id;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private String transactionId;

    public static PaymentResponse toDto(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .method(payment.getPaymentMethod())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .build();
    }
}
