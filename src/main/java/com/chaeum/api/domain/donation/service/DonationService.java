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

        List<Donation> donations = donationRepository.findByMemberIdAndYearAndMonth(memberId, currentYear, currentMonth);
        BigDecimal total = BigDecimal.ZERO;

        for (Donation donation : donations) {
            total = total.add(donation.getAmount());
        }

        return total;
    }

    public BigDecimal getThisYearTotalByMemberId(Long memberId) {
        int currentYear = LocalDateTime.now().getYear();
        List<Donation> donations = donationRepository.findByMemberIdAndYear(memberId, currentYear);
        BigDecimal total = BigDecimal.ZERO;

        for (Donation donation : donations) {
            total = total.add(donation.getAmount());
        }

        return total;
    }
}
