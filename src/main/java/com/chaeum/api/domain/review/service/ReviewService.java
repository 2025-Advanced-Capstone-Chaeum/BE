package com.chaeum.api.domain.review.service;

import com.chaeum.api.domain.donation.service.DonationService;
import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.funding.service.FundingService;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.domain.review.dto.request.ReviewCreateRequest;
import com.chaeum.api.domain.review.dto.request.ReviewUpdateRequest;
import com.chaeum.api.domain.review.dto.response.ReviewResponse;
import com.chaeum.api.domain.review.dto.response.ReviewSummaryResponse;
import com.chaeum.api.domain.review.entity.Review;
import com.chaeum.api.domain.review.event.ReviewEvent;
import com.chaeum.api.domain.review.repository.ReviewRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.file.entity.UploadedFile;
import com.chaeum.api.global.file.service.FileService;
import com.chaeum.api.global.pagination.cursorResult.CreatedAtCursorResult;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FundingService fundingService;
    private final FileService fileService;
    private final DonationService donationService;
    private final MemberService memberService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Long save(Long fundingId, ReviewCreateRequest reviewCreateRequest) {
        Funding funding = fundingService.findById(fundingId);
        funding.validateStatusCompleted();
        funding.validateGoalReached();
        List<UploadedFile> files = fileService.getUploadedFilesByUrls(reviewCreateRequest.getImageUrls());
        Review review = Review.toEntity(funding, files, reviewCreateRequest);
        funding.markReviewed();
        Review savedReview = reviewRepository.save(review);

        List<Long> memberIds = donationService.getDonatedMemberIdsByFundingId(fundingId);
        memberIds.forEach(
            memberId -> {
                Member member = memberService.findById(memberId);
                eventPublisher.publishEvent(ReviewEvent.from(savedReview, member));
            }
        );

        return savedReview.getId();
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReviewDetails(Long fundingId) {
        Review review = getReview(fundingId);
        return ReviewResponse.toDto(review);
    }

    @Transactional(readOnly = true)
    public CreatedAtCursorResult<ReviewSummaryResponse> getReviews(LocalDateTime cursor, int limit) {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewSummaryResponse> filteredReviews = reviews.stream()
            .filter(review -> cursor == null || review.getCreatedAt().isAfter(cursor))
            .sorted(Comparator.comparing(Review::getCreatedAt).reversed())
            .map(ReviewSummaryResponse::toDto)
            .collect(Collectors.toList());

        return CreatedAtCursorResult.of(filteredReviews, cursor, limit);
    }

    @Transactional
    public Long update(Long fundingId, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = getReview(fundingId);
        List<UploadedFile> files = fileService.getUploadedFilesByUrls(reviewUpdateRequest.getImageUrls());
        review.update(files, reviewUpdateRequest);
        return review.getId();
    }

    @Transactional
    public Long delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return reviewId;
    }

    private Review getReview(Long fundingId) {
        return reviewRepository.findByFundingId(fundingId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.REVIEW_NOT_FOUND));
    }
}
