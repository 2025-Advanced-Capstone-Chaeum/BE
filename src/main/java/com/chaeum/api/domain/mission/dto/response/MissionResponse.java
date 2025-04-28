package com.chaeum.api.domain.mission.dto.response;

import com.chaeum.api.domain.mission.entity.Mission;
import com.chaeum.api.global.pagination.provider.IdProvider;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionResponse implements IdProvider {

    private Long id;

    private String name;

    private String description;

    private String missionImage;

    public static MissionResponse toDto(Mission mission) {
        return MissionResponse.builder()
            .id(mission.getId())
            .name(mission.getName())
            .description(mission.getDescription())
            .missionImage(mission.getMissionImageUrl())
            .build();
    }
}
