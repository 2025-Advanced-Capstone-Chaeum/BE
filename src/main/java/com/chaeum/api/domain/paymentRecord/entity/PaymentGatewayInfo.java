package com.chaeum.api.domain.paymentRecord.entity;

import com.chaeum.api.domain.paymentRecord.dto.request.PaymentGatewayInfoRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentGatewayInfo {

    @Column(name = "imp_uid", length = 255)
    private String impUid;

    @Column(name = "merchant_uid", length = 255)
    private String merchantUid;

    @Column(name = "pg_provider", length = 100)
    private String PgProvider;

    @Column(name = "fail_reason")
    private String failReason;

    public static PaymentGatewayInfo create(PaymentGatewayInfoRequest paymentGatewayInfoRequest) {
        return PaymentGatewayInfo.builder()
                .impUid(paymentGatewayInfoRequest.getImportUid())
                .merchantUid(paymentGatewayInfoRequest.getMerchantUid())
                .PgProvider(paymentGatewayInfoRequest.getGatewayProvider())
                .failReason(paymentGatewayInfoRequest.getFailReason())
                .build();
    }
}
