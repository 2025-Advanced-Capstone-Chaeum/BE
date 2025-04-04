package com.chaeum.api.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateRequest {

    @NotNull
    @Schema(description = "이름", example = "김민상")
    private String name;

    @NotNull
    @Schema(description = "프로필 이미지 URL", example = "https://chaeum.site/image.png")
    private String profileImage;
}
