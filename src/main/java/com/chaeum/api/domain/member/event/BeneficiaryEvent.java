package com.chaeum.api.domain.member.event;

import com.chaeum.api.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BeneficiaryEvent {

    private Member receiver;

    private String content;

    public static BeneficiaryEvent createSuccess(Member receiver) {
        String message = "수혜자 등록이 승인되었습니다.";
        return BeneficiaryEvent.builder()
            .receiver(receiver)
            .content(message)
            .build();
    }

    public static BeneficiaryEvent createFailure(Member receiver) {
        String message = "수혜자 등록이 거절되었습니다.";
        return BeneficiaryEvent.builder()
            .receiver(receiver)
            .content(message)
            .build();
    }
}
