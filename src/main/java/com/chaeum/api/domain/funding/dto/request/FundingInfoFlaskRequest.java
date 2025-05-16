package com.chaeum.api.domain.funding.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FundingInfoFlaskRequest {

    private Long fundingId;

    private String title;

    private String content;

    public static FundingInfoFlaskRequest create(Long fundingId, String title, String content) {
        return FundingInfoFlaskRequest.builder()
            .fundingId(fundingId)
            .title(title)
            .content(content)
            .build();
    }
}
