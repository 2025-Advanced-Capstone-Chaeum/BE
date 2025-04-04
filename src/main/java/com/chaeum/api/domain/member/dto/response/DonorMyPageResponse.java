package com.chaeum.api.domain.member.dto.response;

import com.chaeum.api.domain.donation.dto.response.DonationSummaryResponse;
import com.chaeum.api.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class DonorMyPageResponse implements MemberMyPageResponse {

    private String name;

    private String email;

    private String profileImage;

    private BigDecimal monthlyAmount;

    private BigDecimal yearlyAmount;

    private List<DonationSummaryResponse> donations;

    public static DonorMyPageResponse toDto(
            Member member,
            BigDecimal monthlyAmount,
            BigDecimal yearlyAmount,
            List<DonationSummaryResponse> donations
    ) {
        return DonorMyPageResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .monthlyAmount(monthlyAmount)
                .yearlyAmount(yearlyAmount)
                .donations(donations)
                .build();
    }
}
