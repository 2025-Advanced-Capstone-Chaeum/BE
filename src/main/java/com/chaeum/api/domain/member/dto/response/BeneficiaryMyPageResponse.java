package com.chaeum.api.domain.member.dto.response;

import com.chaeum.api.domain.funding.dto.response.FundingSummaryResponse;
import com.chaeum.api.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BeneficiaryMyPageResponse implements MemberMyPageResponse {

    private String name;

    private String email;

    private String profileImage;

    private List<FundingSummaryResponse> fundings;

    public static BeneficiaryMyPageResponse toDto(Member member, List<FundingSummaryResponse> fundings) {
        return BeneficiaryMyPageResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .fundings(fundings)
                .build();
    }
}
