package com.chaeum.api.domain.review.event;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewEvent {

    private Long id;

    private Member receiver;

    private String fundingImageUrl;

    private String content;

    public static ReviewEvent from(Review review, Member receiver) {
        String message = "펀딩 후기가 도착했습니다.";
        return ReviewEvent.builder()
            .id(review.getId())
            .receiver(receiver)
            .fundingImageUrl(review.getFunding().getFundingImages().getFirst().getFileUrl())
            .content(message)
            .build();
    }
}
