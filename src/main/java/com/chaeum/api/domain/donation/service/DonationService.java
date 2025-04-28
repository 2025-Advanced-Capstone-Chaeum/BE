package com.chaeum.api.domain.donation.service;

import com.chaeum.api.domain.donation.dto.request.DonationCreateRequest;
import com.chaeum.api.domain.donation.dto.response.DonationCreateResponse;
import com.chaeum.api.domain.donation.dto.response.DonationSummaryResponse;
import com.chaeum.api.domain.donation.entity.Donation;
import com.chaeum.api.domain.donation.event.DonationEvent;
import com.chaeum.api.domain.donation.repository.DonationRepository;
import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.funding.service.FundingService;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.title.service.TitleService;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;
    private final FundingService fundingService;
    private final LoginMemberProvider loginMemberProvider;
    private final TitleService titleService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public DonationCreateResponse save(DonationCreateRequest request) {
        Member member = loginMemberProvider.getCurrentLoginMember();
        member.validatePointInsufficient(request.getPoint());

        Funding funding = fundingService.findById(request.getFundingId());
        Donation donation = Donation.toEntity(request, member, funding);
        Donation savedDonation = donationRepository.save(donation);
        eventPublisher.publishEvent(
            DonationEvent.from(savedDonation, member, funding.getMember())
        );

        titleService.giveTitle(getDonationCountByMember(member));

        return DonationCreateResponse.toDto(savedDonation);
    }

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

    @Transactional
    public Long delete(Long donationId) {
        donationRepository.deleteById(donationId);
        return donationId;
    }

    public Donation findById(Long id) {
        return donationRepository.findById(id)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.DONATION_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public int getCountSharedDonations(Member member1, Member member2) {
        return donationRepository.countSameFundingDonations(member1.getId(), member2.getId());
    }

    @Transactional(readOnly = true)
    public Long getDonationCountByMember(Member member) {
        return donationRepository.countByMemberId(member.getId());
    }

    @Transactional(readOnly = true)
    public List<Long> getDonatedMemberIdsByFundingId(Long fundingId) {
        List<Donation> donations = donationRepository.findByFundingId(fundingId);
        return donations.stream()
            .map(donation -> donation.getMember().getId())
            .distinct()
            .toList();
    }
}
