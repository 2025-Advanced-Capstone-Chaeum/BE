package com.chaeum.api.domain.donation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonationInteractionReward {

    private String interactionType;

    private int quantity;

    public static DonationInteractionReward create(String interactionType, int quantity) {
        return DonationInteractionReward.builder()
            .interactionType(interactionType)
            .quantity(quantity)
            .build();
    }
}
