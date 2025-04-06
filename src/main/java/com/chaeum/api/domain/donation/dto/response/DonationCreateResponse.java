package com.chaeum.api.domain.donation.dto.response;

import com.chaeum.api.domain.donation.entity.Donation;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonationCreateResponse {

    private Long id;

    private Long fundingId;

    private BigDecimal amount;

    public static DonationCreateResponse toDto(Donation donation) {
        return DonationCreateResponse.builder()
            .id(donation.getId())
            .fundingId(donation.getFunding().getId())
            .amount(donation.getAmount())
            .build();
    }
}
