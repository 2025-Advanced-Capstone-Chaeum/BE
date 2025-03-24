package com.chaeum.api.domain.payment.entity;

import com.chaeum.api.domain.payment.dto.request.PaymentGatewayInfoRequest;
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

    @Column(name = "import_uid", length = 255)
    private String importUid;

    @Column(name = "merchant_uid", length = 255)
    private String merchantUid;

    @Column(name = "pg_provider", length = 100)
    private String gatewayProvider;

    @Column(name = "fail_reason")
    private String failReason;

    public static PaymentGatewayInfo create(PaymentGatewayInfoRequest paymentGatewayInfoRequest) {
        return PaymentGatewayInfo.builder()
                .importUid(paymentGatewayInfoRequest.getImportUid())
                .merchantUid(paymentGatewayInfoRequest.getMerchantUid())
                .gatewayProvider(paymentGatewayInfoRequest.getGatewayProvider())
                .failReason(paymentGatewayInfoRequest.getFailReason())
                .build();
    }
}
