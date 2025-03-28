package com.chaeum.api.domain.paymentRecord.dto.response;

import com.chaeum.api.domain.paymentRecord.entity.PaymentRecord;
import com.chaeum.api.domain.paymentRecord.entity.PaymentMethod;
import com.chaeum.api.domain.paymentRecord.entity.PaymentStatus;
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

    public static PaymentResponse toDto(PaymentRecord paymentRecord) {
        return PaymentResponse.builder()
                .id(paymentRecord.getId())
                .amount(paymentRecord.getAmount())
                .method(paymentRecord.getPaymentMethod())
                .status(paymentRecord.getStatus())
                .transactionId(paymentRecord.getTransactionId())
                .build();
    }
}
