package com.chaeum.api.domain.payment.dto.request;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.payment.entity.PaymentMethod;
import com.chaeum.api.domain.payment.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentCreateRequest {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String transactionId;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private PaymentStatus status;

    @NotNull
    private PaymentGatewayInfoRequest paymentGatewayInfoRequest;
}
