package com.chaeum.api.domain.funding.service;

import com.chaeum.api.domain.funding.dto.request.FundingCreateRequest;
import com.chaeum.api.domain.funding.dto.request.FundingUpdateRequest;
import com.chaeum.api.domain.funding.dto.response.FundingResponse;
import com.chaeum.api.domain.funding.dto.response.FundingSummaryResponse;
import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.funding.entity.FundingStatus;
import com.chaeum.api.domain.funding.event.FundingEvent;
import com.chaeum.api.domain.funding.repository.FundingRepository;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.pagination.cursorResult.IdCursorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;
    private final LoginMemberProvider loginMemberProvider;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Long save(FundingCreateRequest fundingCreateRequest) {
        Member member = loginMemberProvider.getCurrentLoginMember();
        Funding funding = Funding.toEntity(fundingCreateRequest, member);
        Funding savedFunding = fundingRepository.save(funding);

        eventPublisher.publishEvent(
            FundingEvent.from(savedFunding, member)
        );
        System.out.println("이벤트 처리 ");

        return funding.getId();
    }

    @Transactional(readOnly = true)
    public FundingResponse getFunding(Long fundingId) {
        Funding funding = findById(fundingId);
        return FundingResponse.toDto(funding);
    }

    @Transactional(readOnly = true)
    public IdCursorResult<FundingResponse> getFundingsByCondition(
            FundingStatus status, String title, Long cursor, int limit
    ) {
        List<Funding> filteredFundings = fundingRepository.findAll().stream()
                .filter(funding -> isStatusMatch(funding, status))
                .filter(funding -> isTitleMatch(funding, title))
                .filter(funding -> isCursorAfter(funding, cursor))
                .sorted(Comparator
                        .comparing(Funding::getCreatedAt, Comparator.reverseOrder())
                        .thenComparing(Funding::getId, Comparator.reverseOrder())
                )
                .limit(limit)
                .toList();

        List<FundingResponse> responses = filteredFundings.stream()
                .map(FundingResponse::toDto)
                .toList();

        return IdCursorResult.of(responses, cursor, limit);
    }

    @Transactional
    public Long update(Long fundingId, FundingUpdateRequest fundingUpdateRequest) {
        Funding funding = findById(fundingId);
        funding.update(fundingUpdateRequest);
        return funding.getId();
    }

    @Transactional
    public Long delete(Long fundingId) {
        fundingRepository.deleteById(fundingId);
        return fundingId;
    }

    @Transactional(readOnly = true)
    public List<FundingSummaryResponse> getFundingSummariesByMemberId(Long memberId) {
        return fundingRepository.findByMemberIdOrderByCreatedAtDesc(memberId).stream()
                .map(FundingSummaryResponse::toDto)
                .toList();
    }

    @Scheduled(fixedRate = 60 * 1000) // 1분마다 실행
    @Transactional
    public void updateFundingStatusAutomatically() {
        fundingRepository.findByStatusAndEndDateBefore(FundingStatus.ONGOING, LocalDateTime.now())
                .forEach(Funding::markAsCompleted);
    }

    // 조건별 펀딩 조회 검증
    private boolean isStatusMatch(Funding funding, FundingStatus status) {
        return status == null || funding.getStatus() == status;
    }

    private boolean isTitleMatch(Funding funding, String keyword) {
        return keyword == null || keyword.isBlank()
                || funding.getTitle().toLowerCase().contains(keyword.toLowerCase());
    }

    private boolean isCursorAfter(Funding funding, Long cursor) {
        return cursor == null || funding.getId() > cursor;
    }

    // Id로 조회
    public Funding findById(Long fundingId) {
        return fundingRepository.findById(fundingId)
                .orElseThrow(() -> ChaeumException.from(ErrorCode.FUNDING_NOT_FOUND));
    }
}
