package com.chaeum.api.domain.donation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationCreateRequest {

    @NotNull
    @Schema(description = "펀딩 id", example = "1")
    private Long fundingId;

    @NotNull
    @DecimalMin(value = "0", message = "기부 금액은 음수가 될 수 없습니다.")
    @Schema(description = "기부 금액", example = "5000")
    private BigDecimal amount;

    @NotNull
    @DecimalMin(value = "0", message = "사용 포인트는 음수가 될 수 없습니다.")
    @Schema(description = "사용 포인트", example = "0")
    private BigDecimal point;
}
