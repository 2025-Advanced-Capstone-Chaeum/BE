package com.chaeum.api.domain.memberMission.service;

import static com.chaeum.api.global.utils.MemberMissionConstants.ATTENDANCE_PROGRESS_MIN;
import static com.chaeum.api.global.utils.MemberMissionConstants.CAT_EXP_PROGRESS_MAX;
import static com.chaeum.api.global.utils.MemberMissionConstants.CAT_EXP_PROGRESS_MIN;
import static com.chaeum.api.global.utils.MemberMissionConstants.CAT_INTERACTION_PROGRESS_MAX;
import static com.chaeum.api.global.utils.MemberMissionConstants.CAT_INTERACTION_PROGRESS_MIN;
import static com.chaeum.api.global.utils.MemberMissionConstants.DONATION_PROGRESS_MAX;
import static com.chaeum.api.global.utils.MemberMissionConstants.DONATION_PROGRESS_MIN;
import static com.chaeum.api.global.utils.MemberMissionConstants.ITEM_WEAR_PROGRESS_MIN;
import static com.chaeum.api.global.utils.MemberMissionConstants.MEMBER_MISSION_ASSIGN_COUNT;

import com.chaeum.api.domain.mission.entity.Mission;
import com.chaeum.api.domain.mission.entity.MissionType;
import com.chaeum.api.domain.mission.service.MissionService;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberMissionRandomService {

    private final MissionService missionService;

    @Transactional(readOnly = true)
    public List<Mission> getRandomMissions() {
        List<Mission> missions = missionService.findAllMissions();
        Collections.shuffle(missions);
        return missions.stream()
            .limit(MEMBER_MISSION_ASSIGN_COUNT)
            .toList();
    }

    @Transactional(readOnly = true)
    public int getRandomProgressCount(MissionType type) {
        return switch (type) {
            case ATTENDANCE -> ATTENDANCE_PROGRESS_MIN;
            case CAT_EXP -> getRandomNumberInRange(CAT_EXP_PROGRESS_MIN, CAT_EXP_PROGRESS_MAX);
            case CAT_INTERACTION -> getRandomNumberInRange(CAT_INTERACTION_PROGRESS_MIN, CAT_INTERACTION_PROGRESS_MAX);
            case DONATION -> getRandomNumberInRange(DONATION_PROGRESS_MIN, DONATION_PROGRESS_MAX);
            case ITEM_WEAR -> ITEM_WEAR_PROGRESS_MIN;
        };
    }

    private int getRandomNumberInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
