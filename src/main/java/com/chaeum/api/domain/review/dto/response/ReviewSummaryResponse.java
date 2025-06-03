package com.chaeum.api.domain.review.dto.response;

import com.chaeum.api.domain.review.entity.Review;
import com.chaeum.api.global.file.dto.ExternalFileResponse;
import com.chaeum.api.global.file.entity.UploadedFile;
import com.chaeum.api.global.pagination.provider.CreatedAtProvider;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewSummaryResponse implements CreatedAtProvider {

    private Long id;

    private Long fundingId;

    private String title;

    private ExternalFileResponse reviewImage;

    private LocalDateTime createdAt;

    public static ReviewSummaryResponse toDto(Review review) {
        Optional<UploadedFile> firstFile = review.getReviewImages().stream().findFirst();
        ExternalFileResponse imageDto = firstFile
            .map(ExternalFileResponse::toDto)
            .orElse(null);

        return ReviewSummaryResponse.builder()
            .id(review.getId())
            .fundingId(review.getFunding().getId())
            .title(review.getTitle())
            .reviewImage(imageDto)
            .createdAt(review.getCreatedAt())
            .build();
    }
}
