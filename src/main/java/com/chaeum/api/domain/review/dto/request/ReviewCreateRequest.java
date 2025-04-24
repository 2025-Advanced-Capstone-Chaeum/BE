package com.chaeum.api.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {

    @NotNull
    @Size(max = 100, message = "펀딩 후기 제목은 최대 100자까지 입력할 수 있습니다.")
    @Schema(description = "펀딩 후기 제목", example = "후원해주셔서 정말 감사합니다.")
    private String title;

    @NotNull
    @Size(max = 1000, message = "펀딩 후기 내용은 최대 1000자까지 입력할 수 있습니다.")
    @Schema(description = "펀딩 내용", example = "덕분에 사정이 나아졌습니다.")
    private String content;

    @NotNull
    @Schema(
        description = "리뷰 사진 URL",
        example = "[\"https://s3.ap-northeast-2.amazonaws.com/item/764q13ef-7301-22D2-a1d4-e7c8cw4.png\"]"
    )
    private List<String> imageUrls;
}
