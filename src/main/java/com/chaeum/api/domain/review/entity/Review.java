package com.chaeum.api.domain.review.entity;

import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.review.dto.request.ReviewCreateRequest;
import com.chaeum.api.domain.review.dto.request.ReviewUpdateRequest;
import com.chaeum.api.global.entity.BaseEntity;
import com.chaeum.api.global.file.entity.UploadedFile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_images")
    private List<UploadedFile> reviewImages;

    public static Review toEntity(
        Funding funding, List<UploadedFile> files, ReviewCreateRequest reviewCreateRequest
    ) {
        return Review.builder()
            .funding(funding)
            .title(reviewCreateRequest.getTitle())
            .content(reviewCreateRequest.getContent())
            .reviewImages(files)
            .build();
    }

    public void update(List<UploadedFile> files, ReviewUpdateRequest reviewUpdateRequest) {
        Optional.ofNullable(reviewUpdateRequest.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(reviewUpdateRequest.getContent()).ifPresent(this::setContent);
        Optional.ofNullable(files).ifPresent(this::setReviewImages);
    }
}
