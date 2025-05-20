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
    @Schema(description = "최종 결제 금액 (단위: 원)", example = "1000")
    private BigDecimal amount;

    @NotNull
    @Schema(description = "사용할 포인트", example = "0")
    private BigDecimal points;

    @NotNull
    @Schema(description = "트랜잭션 Id", example = "14219299348")
    private String transactionId;

    @NotNull
    @Schema(
        description = "결제 수단",
        example = "KAKAO_PAY",
        allowableValues = {"PAYCO", "TOSS_PAY", "KAKAO_PAY"}
    )
    private PaymentMethod paymentMethod;

    @NotNull
    @Schema(
        description = "결제 상태",
        example = "PENDING",
        allowableValues = {"PENDING", "COMPLETED", "FAILED", "CANCELED"}
    )
    private PaymentStatus status;

    @NotNull
    @Schema(description = "아임포트 결제 고유 ID", example = "imp_961458855243")
    private String impUid;

    @NotNull
    @Schema(description = "우리 시스템에서 발급한 주문 번호", example = "donation_1747389457724_6")
    private String merchantUid;

    @NotNull
    @Schema(description = "PG사 이름 (예: kakaopay, tosspay, payco)", example = "kakaopay")
    private String gatewayProvider;

    @Schema(
        description = """
            결제 실패 사유입니다. 결제가 실패한 경우에만 포함됩니다.<br>
            예시: 잔액 부족, 카드 한도 초과, 사용자 취소, PG사 오류 등
            """,
        example = "잔액 부족",
        nullable = true
    )
    private String failReason;
}
