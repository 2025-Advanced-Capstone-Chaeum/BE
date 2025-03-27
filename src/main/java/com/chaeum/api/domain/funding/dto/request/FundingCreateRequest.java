package com.chaeum.api.domain.funding.dto.request;

import com.chaeum.api.domain.funding.entity.FundingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FundingCreateRequest {

    @NotNull
    @Schema(description = "펀딩 제목", example = "공부해서 사회에 보답하겠습니다.")
    private String title;

    @NotNull
    @Schema(description = "펀딩 내용", example = "학업에 필요한 교재와 책상을 마련하기 위한 펀딩입니다.")
    private String content;

    @NotNull
    @Schema(description = "펀딩 이미지 URL", example = "https://chaeum.site/image.jpg")
    private String fundingImage;

    @NotNull
    @Schema(description = "물품 구매 링크", example = "https://chaeum.site/item")
    private String itemLink;

    @NotNull
    @Schema(description = "주소", example = "경기도 수원시 장안구")
    private String address;

    @NotNull
    @Schema(description = "목표 금액", example = "1000000.00")
    private BigDecimal goalAmount;

    @NotNull
    @Schema(description = "현재 모금액", example = "900000.00")
    private BigDecimal currentAmount;

    @NotNull
    @Schema(description = "펀딩 상태", example = "ONGOING")
    private FundingStatus status;

    @NotNull
    @Schema(description = "펀딩 종료일", example = "2025-03-28T23:59:59")
    private LocalDateTime endDate;
}
