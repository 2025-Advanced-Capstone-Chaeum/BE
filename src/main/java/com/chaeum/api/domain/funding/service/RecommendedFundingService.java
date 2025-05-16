package com.chaeum.api.domain.funding.service;

import com.chaeum.api.domain.donation.entity.Donation;
import com.chaeum.api.domain.funding.dto.request.FundingInfoFlaskRequest;
import com.chaeum.api.domain.funding.dto.request.FundingRecommendFlaskRequest;
import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.funding.entity.RecommendedFunding;
import com.chaeum.api.domain.funding.repository.RecommendedFundingRepository;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendedFundingService {

    private final RecommendedFundingRepository recommendedFundingRepository;
    private final FundingService fundingService;
    private final LoginMemberProvider loginMemberProvider;
    private final RestTemplate restTemplate;

    @Value("${ai.server.recommend}")
    private String recommendServerEndpoint;

    @Transactional
    public void recommendFunding(Funding donatedFunding, List<Donation> myDonations) {
        // 전체 펀딩 리스트 조회
        List<FundingInfoFlaskRequest> allFundingInfos = getAllFundingInfos();

        // 내가 기부한 펀딩 리스트 조회
        List<FundingInfoFlaskRequest> myFundingInfos = getMyFundingInfos(myDonations);

        // 방금 기부한 펀딩 정보 조회
        FundingInfoFlaskRequest currentDonatedFundingInfo = mapToFundingInfoFlaskRequest(donatedFunding);

        // Flask에 요청할 DTO 조합
        FundingRecommendFlaskRequest request =
            FundingRecommendFlaskRequest.create(allFundingInfos, myFundingInfos, currentDonatedFundingInfo);

        // AI 서버에 추천 요청
        Long[] responseIds = requestRecommendFundingToFlask(request);

        // 해당 회원의 RecommendedFunding 테이블 전체 삭제
        Member member = loginMemberProvider.getCurrentLoginMember();
        recommendedFundingRepository.deleteByMemberId(member.getId());

        // 새로운 추천 저장
        List<Long> recommendedFundingIds = List.of(responseIds);
        List<RecommendedFunding> recommendedFundings = recommendedFundingIds.stream()
            .map(fundingId -> RecommendedFunding.create(fundingId, member))
            .toList();

        recommendedFundingRepository.saveAll(recommendedFundings);
    }

    private Long[] requestRecommendFundingToFlask(FundingRecommendFlaskRequest request) {
        Long[] recommendedFundingIds = restTemplate.postForObject(
            recommendServerEndpoint, request, Long[].class
        );
        return recommendedFundingIds == null ? new Long[0] : recommendedFundingIds;
    }

    private List<FundingInfoFlaskRequest> getAllFundingInfos() {
        List<Funding> allFundings = fundingService.findAll();
        return allFundings.stream()
            .map(this::mapToFundingInfoFlaskRequest)
            .toList();
    }

    private List<FundingInfoFlaskRequest> getMyFundingInfos(List<Donation> myDonations) {
        return myDonations.stream()
            .map(Donation::getFunding)
            .map(this::mapToFundingInfoFlaskRequest)
            .toList();
    }

    private FundingInfoFlaskRequest mapToFundingInfoFlaskRequest(Funding funding) {
        return FundingInfoFlaskRequest.create(funding.getId(), funding.getTitle(), funding.getContent());
    }
}
