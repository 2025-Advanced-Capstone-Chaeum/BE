package com.chaeum.api.domain.funding.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FundingRecommendFlaskRequest {

    List<FundingInfoFlaskRequest> allFundings;

    List<FundingInfoFlaskRequest> myDonatedFundings;

    FundingInfoFlaskRequest currentDonatedFunding;

    public static FundingRecommendFlaskRequest create(
        List<FundingInfoFlaskRequest> allFunding,
        List<FundingInfoFlaskRequest> myDonatedFundings,
        FundingInfoFlaskRequest currentDonatedFunding
    ) {
        return FundingRecommendFlaskRequest.builder()
            .allFundings(allFunding)
            .myDonatedFundings(myDonatedFundings)
            .currentDonatedFunding(currentDonatedFunding)
            .build();
    }
}
