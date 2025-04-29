package com.chaeum.api.domain.memberMission.dto.response;

import com.chaeum.api.domain.memberMission.entity.MemberMission;
import com.chaeum.api.domain.memberMission.entity.MissionStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberMissionResponse {

    private Long missionId;

    private String missionName;

    private String missionDescription;

    private String missionImageUrl;

    private MissionStatus status;

    private int currentCount;

    private int progressCount;

    public static MemberMissionResponse toDto(MemberMission memberMission) {
        return MemberMissionResponse.builder()
            .missionId(memberMission.getMission().getId())
            .missionName(memberMission.getMission().getName())
            .missionDescription(memberMission.getMission().getDescription())
            .missionImageUrl(memberMission.getMission().getMissionImageUrl())
            .status(memberMission.getStatus())
            .currentCount(memberMission.getCurrentCount())
            .progressCount(memberMission.getProgressCount())
            .build();
    }
}
