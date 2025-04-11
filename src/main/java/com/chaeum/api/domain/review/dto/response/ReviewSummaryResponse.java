package com.chaeum.api.domain.review.dto.response;

import com.chaeum.api.domain.review.entity.Review;
import com.chaeum.api.global.file.dto.ExternalFileResponse;
import com.chaeum.api.global.file.entity.UploadedFile;
import com.chaeum.api.global.pagination.provider.CreatedAtProvider;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewSummaryResponse implements CreatedAtProvider {

    private Long id;

    private String title;

    private ExternalFileResponse reviewImage;

    private LocalDateTime createdAt;

    public static ReviewSummaryResponse toDto(Review review) {
        UploadedFile file = review.getReviewImages().getFirst();
        return ReviewSummaryResponse.builder()
            .id(review.getId())
            .title(review.getTitle())
            .reviewImage(ExternalFileResponse.toDto(file))
            .createdAt(review.getCreatedAt())
            .build();
    }
}
