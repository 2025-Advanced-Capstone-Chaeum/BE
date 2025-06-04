package com.chaeum.api.domain.funding.dto.response;

import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.funding.entity.FundingStatus;
import com.chaeum.api.global.file.dto.ExternalFileResponse;
import com.chaeum.api.global.file.entity.UploadedFile;
import com.chaeum.api.global.pagination.provider.IdProvider;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class FundingResponse implements IdProvider {

    private Long id;

    private String name;

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

    public static FundingResponse toDto(Funding funding) {
        List<UploadedFile> files = funding.getFundingImages();
        return FundingResponse.builder()
            .id(funding.getId())
            .name(funding.getMember().getName())
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
