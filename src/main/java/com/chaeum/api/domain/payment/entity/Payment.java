package com.chaeum.api.domain.payment.entity;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.payment.dto.request.PaymentCreateRequest;
import com.chaeum.api.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "payment")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "transaction_id", length = 255)
    private String transactionId;

    @Embedded
    private PaymentGatewayInfo gatewayInfo;

    public static Payment create(
            Member member,
            PaymentCreateRequest paymentCreateRequest,
            PaymentGatewayInfo paymentGatewayInfo
    ) {
        return Payment.builder()
                .member(member)
                .amount(paymentCreateRequest.getAmount())
                .paymentMethod(paymentCreateRequest.getPaymentMethod())
                .status(paymentCreateRequest.getStatus())
                .transactionId(paymentCreateRequest.getTransactionId())
                .gatewayInfo(paymentGatewayInfo)
                .build();
    }
}
