package com.chaeum.api.domain.member.service;

import com.chaeum.api.domain.donation.dto.response.DonationSummaryResponse;
import com.chaeum.api.domain.donation.service.DonationService;
import com.chaeum.api.domain.funding.dto.response.FundingSummaryResponse;
import com.chaeum.api.domain.funding.service.FundingService;
import com.chaeum.api.domain.member.dto.request.MemberUpdateRequest;
import com.chaeum.api.domain.member.dto.response.BeneficiaryMyPageResponse;
import com.chaeum.api.domain.member.dto.response.DonorMyPageResponse;
import com.chaeum.api.domain.member.dto.response.MemberMyPageResponse;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.dto.CustomMemberDetails;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final DonationService donationService;
    private final FundingService fundingService;

    public Member getCurrentLoginMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw ChaeumException.from(ErrorCode.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomMemberDetails customMemberDetails) {
            return customMemberDetails.getMember();
        }
        throw ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND);
    }

    public Long getCurrentLoginMemberId() {
        return getCurrentLoginMember().getId();
    }

    @Transactional(readOnly = true)
    public MemberMyPageResponse getMemberMyPage() {
        Member member = getCurrentLoginMember();
        return member.getIsBeneficiary() ? getBeneficiaryMyPage(member) : getDonorMyPage(member);
    }

    @Transactional
    public Long update(Long memberId, MemberUpdateRequest memberUpdateRequest) {
        Member member = findById(memberId);
        member.update(memberUpdateRequest);
        return member.getId();
    }

    @Transactional
    public Long delete(Long memberId) {
        memberRepository.deleteById(memberId);
        return memberId;
    }

    private MemberMyPageResponse getBeneficiaryMyPage(Member member) {
        List<FundingSummaryResponse> fundings = fundingService.getFundingSummariesByMemberId(member.getId());
        return BeneficiaryMyPageResponse.toDto(member, fundings);
    }

    private MemberMyPageResponse getDonorMyPage(Member member) {
        Long memberId = member.getId();
        BigDecimal monthlyAmount = donationService.getThisMonthTotalByMemberId(memberId);
        BigDecimal yearlyAmount = donationService.getThisYearTotalByMemberId(memberId);
        List<DonationSummaryResponse> donations = donationService.getDonationSummariesByMemberId(memberId);
        return DonorMyPageResponse.toDto(member, monthlyAmount, yearlyAmount, donations);
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND));
    }
}
