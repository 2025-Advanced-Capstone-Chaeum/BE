package com.chaeum.api.domain.donation.event;

import com.chaeum.api.domain.donation.entity.Donation;
import com.chaeum.api.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonationEvent {

    private Long id;

    private Member sender;

    private Member receiver;

    private String fundingImageUrl;

    private String content;

    public static DonationEvent from(Donation donation, Member sender, Member receiver) {
        String message = sender.getName() + "님이 기부에 참여했습니다.";
        return DonationEvent.builder()
            .id(donation.getId())
            .sender(sender)
            .receiver(receiver)
            .fundingImageUrl(donation.getFunding().getFundingImages().getFirst().getFileUrl())
            .content(message)
            .build();
    }
}
