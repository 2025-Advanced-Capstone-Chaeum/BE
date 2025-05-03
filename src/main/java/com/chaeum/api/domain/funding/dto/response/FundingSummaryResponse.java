package com.chaeum.api.domain.funding.dto.response;

import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.global.file.dto.ExternalFileResponse;
import com.chaeum.api.global.file.entity.UploadedFile;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class FundingSummaryResponse {

    private Long id;

    private String title;

    private ExternalFileResponse previewImage;

    private BigDecimal amount;

    private Boolean isReviewed;

    private LocalDateTime createdAt;

    public static FundingSummaryResponse toDto(Funding funding) {
        Optional<UploadedFile> firstFile = funding.getFundingImages().stream().findFirst();
        ExternalFileResponse imageDto = firstFile
            .map(ExternalFileResponse::toDto)
            .orElse(null);

        return FundingSummaryResponse.builder()
            .id(funding.getId())
            .title(funding.getTitle())
            .previewImage(imageDto)
            .amount(funding.getCurrentAmount())
            .isReviewed(funding.getIsReviewed())
            .createdAt(funding.getCreatedAt())
            .build();
    }
}
