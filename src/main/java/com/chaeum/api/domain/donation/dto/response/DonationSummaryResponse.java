package com.chaeum.api.domain.donation.dto.response;

import com.chaeum.api.domain.donation.entity.Donation;
import com.chaeum.api.global.file.dto.ExternalFileResponse;
import com.chaeum.api.global.file.entity.UploadedFile;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class DonationSummaryResponse {

    private Long id;

    private String title;

    private ExternalFileResponse image;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    public static DonationSummaryResponse toDto(Donation donation) {
        Optional<UploadedFile> firstImage = donation.getFunding().getFundingImages()
            .stream()
            .findFirst();

        ExternalFileResponse imageDto = firstImage
            .map(ExternalFileResponse::toDto)
            .orElse(null);

        return DonationSummaryResponse.builder()
            .id(donation.getId())
            .title(donation.getFunding().getTitle())
            .image(imageDto)
            .amount(donation.getAmount())
            .createdAt(donation.getCreatedAt())
            .build();
    }
}
