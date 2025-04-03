package com.chaeum.api.domain.donation.service;

import com.chaeum.api.domain.donation.entity.Donation;
import com.chaeum.api.domain.donation.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    public BigDecimal getThisMonthTotalByMemberId(Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        return donationRepository.findByMemberIdAndYearAndMonth(memberId, currentYear, currentMonth).stream()
                .map(Donation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getThisYearTotalByMemberId(Long memberId) {
        int currentYear = LocalDateTime.now().getYear();

        return donationRepository.findByMemberIdAndYear(memberId, currentYear).stream()
                .map(Donation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
