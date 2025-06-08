package com.chaeum.api.domain.funding.dto.response;

import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.funding.entity.FundingStatus;
import com.chaeum.api.domain.funding.entity.RecommendedFunding;
import com.chaeum.api.global.file.dto.ExternalFileResponse;
import com.chaeum.api.global.file.entity.UploadedFile;
import com.chaeum.api.global.pagination.provider.IdProvider;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecommendedFundingResponse implements IdProvider {

    private Long id;

    private Long fundingId;

    private String memberName;

    private String memberProfileImageUrl;

    private String title;

    private String content;

    private List<ExternalFileResponse> fundingImages;

    private String itemLink;

    private String address;

    private BigDecimal goalAmount;

    private BigDecimal currentAmount;

    private FundingStatus status;

    private LocalDateTime endDate;

    private Boolean isReviewed;

    private LocalDateTime createdAt;

    public static RecommendedFundingResponse toDto(RecommendedFunding recommendedFunding, Funding funding) {
        List<UploadedFile> files = funding.getFundingImages();
        return RecommendedFundingResponse.builder()
            .id(recommendedFunding.getId())
            .fundingId(funding.getId())
            .memberName(funding.getMember().getName())
            .memberProfileImageUrl(funding.getMember().getProfileImage())
            .title(funding.getTitle())
            .content(funding.getContent())
            .fundingImages(ExternalFileResponse.toListDto(files))
            .itemLink(funding.getItemLink())
            .address(funding.getAddress())
            .goalAmount(funding.getGoalAmount())
            .currentAmount(funding.getCurrentAmount())
            .status(funding.getStatus())
            .endDate(funding.getEndDate())
            .isReviewed(funding.getIsReviewed())
            .createdAt(funding.getCreatedAt())
            .build();
    }
}
