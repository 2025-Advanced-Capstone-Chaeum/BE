package com.chaeum.api.domain.paymentRecord.entity;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.paymentRecord.dto.request.PaymentCreateRequest;
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
@Table(name = "payment_record")
public class PaymentRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_record_id")
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

    @Column(name = "imp_uid", length = 255)
    private String impUid;

    @Column(name = "merchant_uid", length = 255)
    private String merchantUid;

    @Column(name = "pg_provider", length = 100)
    private String pgProvider;

    @Column(name = "fail_reason")
    private String failReason;

    public static PaymentRecord create(Member member, PaymentCreateRequest request) {
        return PaymentRecord.builder()
            .member(member)
            .amount(request.getAmount())
            .paymentMethod(request.getPaymentMethod())
            .status(request.getStatus())
            .transactionId(request.getTransactionId())
            .impUid(request.getImpUid())
            .merchantUid(request.getMerchantUid())
            .pgProvider(request.getGatewayProvider())
            .failReason(request.getFailReason())
            .build();
    }
}
