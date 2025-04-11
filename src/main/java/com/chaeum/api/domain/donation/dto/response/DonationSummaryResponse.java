package com.chaeum.api.domain.donation.dto.response;

import com.chaeum.api.domain.donation.entity.Donation;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class DonationSummaryResponse {

    private Long id;

    private String title;

    private String imageUrl;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    public static DonationSummaryResponse toDto(Donation donation) {
        return DonationSummaryResponse.builder()
            .id(donation.getId())
            .title(donation.getFunding().getTitle())
            .imageUrl(donation.getFunding().getFundingImage())
            .amount(donation.getAmount())
            .createdAt(donation.getCreatedAt())
            .build();
    }
}
