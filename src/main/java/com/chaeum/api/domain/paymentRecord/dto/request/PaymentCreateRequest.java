package com.chaeum.api.domain.paymentRecord.dto.request;

import com.chaeum.api.domain.paymentRecord.entity.PaymentMethod;
import com.chaeum.api.domain.paymentRecord.entity.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentCreateRequest {

    @NotNull
    @Schema(description = "기부 ID", example = "1")
    private Long donationId;

    @NotNull
    @Schema(description = "최종 결제 금액", example = "3000")
    private BigDecimal amount;

    @NotNull
    @Schema(description = "트랜잭션 Id", example = "14219299348")
    private String transactionId;

    @NotNull
    @Schema(description = "결제 방법", example = "PAYCO")
    private PaymentMethod paymentMethod;

    @NotNull
    @Schema(description = "결제 상태", example = "PENDING")
    private PaymentStatus status;

    @NotNull
    private PaymentGatewayInfoRequest paymentGatewayInfoRequest;
}
