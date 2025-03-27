package com.chaeum.api.domain.funding.entity;

import com.chaeum.api.domain.funding.dto.request.FundingCreateRequest;
import com.chaeum.api.domain.funding.dto.request.FundingUpdateRequest;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.entity.BaseEntity;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

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

    @Column(name = "funding_image")
    private String fundingImage;

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

    public static Funding toEntity(FundingCreateRequest fundingCreateRequest, Member member) {
        return Funding.builder()
                .member(member)
                .title(fundingCreateRequest.getTitle())
                .content(fundingCreateRequest.getContent())
                .fundingImage(fundingCreateRequest.getFundingImage())
                .itemLink(fundingCreateRequest.getItemLink())
                .address(fundingCreateRequest.getAddress())
                .goalAmount(fundingCreateRequest.getGoalAmount())
                .currentAmount(fundingCreateRequest.getCurrentAmount())
                .status(fundingCreateRequest.getStatus())
                .endDate(fundingCreateRequest.getEndDate())
                .build();
    }

    public void update(FundingUpdateRequest fundingUpdateRequest) {
        Optional.ofNullable(fundingUpdateRequest.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(fundingUpdateRequest.getContent()).ifPresent(this::setContent);
        Optional.ofNullable(fundingUpdateRequest.getFundingImage()).ifPresent(this::setFundingImage);
        Optional.ofNullable(fundingUpdateRequest.getItemLink()).ifPresent(this::setItemLink);
        Optional.ofNullable(fundingUpdateRequest.getAddress()).ifPresent(this::setAddress);
        Optional.ofNullable(fundingUpdateRequest.getGoalAmount()).ifPresent(this::setGoalAmount);
        Optional.ofNullable(fundingUpdateRequest.getCurrentAmount()).ifPresent(this::setCurrentAmount);
        Optional.ofNullable(fundingUpdateRequest.getStatus()).ifPresent(this::setStatus);
        Optional.ofNullable(fundingUpdateRequest.getEndDate()).ifPresent(this::setEndDate);
    }
}
