package com.chaeum.api.domain.mission.dto.request;

import com.chaeum.api.domain.mission.entity.MissionStatus;
import com.chaeum.api.domain.mission.entity.MissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissionCreateRequest {

    @NotNull
    @Schema(description = "미션 이름", example = "오늘의 출석 체크")
    private String name;

    @NotNull
    @Schema(description = "미션 설명", example = "오늘도 출석해서 고양이를 돌봐주세요!")
    private String description;

    @NotNull
    @Schema(description = "미션 사진 URL", example = "https://bucket.s3.ap-northeast-2.amazonaw.com/item/mission-image.png")
    private String missionImageUrl;

    @NotNull
    @Schema(description = "미션 상태", example = "PENDING")
    private MissionStatus status;

    @NotNull
    @Schema(description = "미션 종류", example = "CHECK_IN")
    private MissionType type;
}
