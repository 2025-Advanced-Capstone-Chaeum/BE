package com.chaeum.api.domain.funding.event;

import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FundingEvent {

    private Long id;

    private Member receiver;

    private String fundingImageUrl;

    private String content;

    public static FundingEvent from(Funding funding, Member receiver) {
        String message = "펀딩이 완료되었습니다. 확인해보세요!";
        return FundingEvent.builder()
            .id(funding.getId())
            .receiver(receiver)
            .fundingImageUrl(funding.getFundingImages().getFirst().getFileUrl())
            .content(message)
            .build();
    }
}
