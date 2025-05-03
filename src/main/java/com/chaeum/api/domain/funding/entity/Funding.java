package com.chaeum.api.domain.funding.entity;

import com.chaeum.api.domain.funding.dto.request.FundingCreateRequest;
import com.chaeum.api.domain.funding.dto.request.FundingUpdateRequest;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.entity.BaseEntity;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.file.entity.UploadedFile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "funding")
public class Funding extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "funding_image")
    private List<UploadedFile> fundingImages;

    @Column(name = "item_link")
    private String itemLink;

    @Column(name = "address")
    private String address;

    @Column(name = "goal_amount", precision = 10, scale = 2)
    private BigDecimal goalAmount;

    @Column(name = "current_amount", precision = 10, scale = 2)
    private BigDecimal currentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FundingStatus status;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "is_reviewed")
    private Boolean isReviewed;

    public static Funding toEntity(FundingCreateRequest fundingCreateRequest, List<UploadedFile> files, Member member) {
        return Funding.builder()
            .member(member)
            .title(fundingCreateRequest.getTitle())
            .content(fundingCreateRequest.getContent())
            .fundingImages(files)
            .itemLink(fundingCreateRequest.getItemLink())
            .address(fundingCreateRequest.getAddress())
            .goalAmount(fundingCreateRequest.getGoalAmount())
            .currentAmount(BigDecimal.ZERO)
            .status(FundingStatus.ONGOING)
            .endDate(fundingCreateRequest.getEndDate())
            .isReviewed(Boolean.FALSE)
            .build();
    }

    public void update(List<UploadedFile> files, FundingUpdateRequest fundingUpdateRequest) {
        Optional.ofNullable(fundingUpdateRequest.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(fundingUpdateRequest.getContent()).ifPresent(this::setContent);
        Optional.ofNullable(files).ifPresent(this::setFundingImages);
        Optional.ofNullable(fundingUpdateRequest.getItemLink()).ifPresent(this::setItemLink);
        Optional.ofNullable(fundingUpdateRequest.getAddress()).ifPresent(this::setAddress);
        Optional.ofNullable(fundingUpdateRequest.getGoalAmount()).ifPresent(this::setGoalAmount);
        Optional.ofNullable(fundingUpdateRequest.getEndDate()).ifPresent(this::setEndDate);
    }

    // 펀딩 완료 처리 메서드
    public void markAsCompleted() {
        if (this.status == FundingStatus.ONGOING) {
            this.status = FundingStatus.COMPLETED;
        }
    }

    public void addCurrentAmount(BigDecimal amount) {
        validateAmount(amount);
        this.currentAmount = this.currentAmount.add(amount);
    }

    public void markReviewed() {
        if (this.isReviewed == Boolean.FALSE) {
            this.isReviewed = Boolean.TRUE;
        }
    }

    public void validateGoalReached() {
        if (this.currentAmount.compareTo(this.goalAmount) < 0) {
            throw ChaeumException.from(ErrorCode.GOAL_AMOUNT_NOT_REACHED);
        }
    }

    public void validateStatusCompleted() {
        if (this.status != FundingStatus.COMPLETED) {
            throw ChaeumException.from(ErrorCode.FUNDING_IS_NOT_COMPLETED);
        }
    }

    private void validateAmount(BigDecimal amount) {
        BigDecimal newTotal = this.currentAmount.add(amount);
        if (newTotal.compareTo(this.goalAmount) > 0) {
            throw ChaeumException.from(ErrorCode.DONATION_AMOUNT_EXCEEDS_GOAL);
        }
    }
}
