package com.chaeum.api.domain.donation.service;

import com.chaeum.api.domain.donation.dto.response.DonationSummaryResponse;
import com.chaeum.api.domain.donation.entity.Donation;
import com.chaeum.api.domain.donation.repository.DonationRepository;
import com.chaeum.api.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    @Transactional(readOnly = true)
    public BigDecimal getThisMonthTotalByMemberId(Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        return donationRepository.findByMemberIdAndYearAndMonth(memberId, currentYear, currentMonth).stream()
            .map(Donation::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public BigDecimal getThisYearTotalByMemberId(Long memberId) {
        int currentYear = LocalDateTime.now().getYear();

        return donationRepository.findByMemberIdAndYear(memberId, currentYear).stream()
            .map(Donation::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public List<DonationSummaryResponse> getDonationSummariesByMemberId(Long memberId) {
        return donationRepository.findByMemberIdOrderByCreatedAtDesc(memberId).stream()
            .map(DonationSummaryResponse::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public int getCountSharedDonations(Member member1, Member member2) {
        return donationRepository.countSameFundingDonations(member1.getId(), member2.getId());
    }
}
