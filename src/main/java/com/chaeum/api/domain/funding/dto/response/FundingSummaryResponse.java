package com.chaeum.api.domain.funding.dto.response;

import com.chaeum.api.domain.funding.entity.Funding;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class FundingSummaryResponse {

    private Long id;

    private String title;

    private String fundingImage;

    private BigDecimal amount;

    private Boolean isReviewed;

    private LocalDateTime createdAt;

    public static FundingSummaryResponse toDto(Funding funding) {
        return FundingSummaryResponse.builder()
                .id(funding.getId())
                .title(funding.getTitle())
                .fundingImage(funding.getFundingImage())
                .amount(funding.getCurrentAmount())
                .isReviewed(funding.getIsReviewed())
                .createdAt(funding.getCreatedAt())
                .build();
    }
}
