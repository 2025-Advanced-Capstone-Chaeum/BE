package com.chaeum.api.domain.donation.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonationRewardResponse {

    private List<DonationInteractionReward> interactionRewards;

    private int pointReward;

    private Long nonInteractionRewardItemId;

    public static DonationRewardResponse create(
        List<DonationInteractionReward> interactionRewards,
        int pointReward,
        Long nonInteractionRewardItemId
    ) {
        return DonationRewardResponse.builder()
            .interactionRewards(interactionRewards)
            .pointReward(pointReward)
            .nonInteractionRewardItemId(nonInteractionRewardItemId)
            .build();
    }
}
