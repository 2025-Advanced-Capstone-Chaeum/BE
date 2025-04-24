package com.chaeum.api.domain.review.dto.response;

import com.chaeum.api.domain.review.entity.Review;
import com.chaeum.api.global.file.dto.ExternalFileResponse;
import com.chaeum.api.global.file.entity.UploadedFile;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {

    private Long id;

    private String title;

    private String content;

    private List<ExternalFileResponse> reviewImages;

    private LocalDateTime createdAt;

    public static ReviewResponse toDto(Review review) {
        List<UploadedFile> files = review.getReviewImages();
        return ReviewResponse.builder()
            .id(review.getId())
            .title(review.getTitle())
            .content(review.getContent())
            .reviewImages(ExternalFileResponse.toListDto(files))
            .createdAt(review.getCreatedAt())
            .build();
    }
}
