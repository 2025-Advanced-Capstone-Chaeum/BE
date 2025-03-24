package com.chaeum.api.domain.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentGatewayInfoRequest {

    @NotNull
    @Schema(description = "아임포트 결제 고유 ID", example = "imp_1234567890")
    private String importUid;

    @NotNull
    @Schema(description = "우리 시스템에서 발급한 주문 번호", example = "donation_20250324_0001")
    private String merchantUid;

    @NotNull
    @Schema(description = "PG사 이름", example = "kakaopay")
    private String gatewayProvider;

    @Schema(description = "결제 실패 원인", example = "잔액 부족")
    private String failReason;
}
