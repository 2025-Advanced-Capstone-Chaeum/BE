package com.chaeum.api.domain.memberMission.service;

import static com.chaeum.api.global.utils.MemberMissionConstants.ATTENDANCE_PROGRESS_MIN;
import static com.chaeum.api.global.utils.MemberMissionConstants.CAT_PROGRESS_MAX;
import static com.chaeum.api.global.utils.MemberMissionConstants.CAT_PROGRESS_MIN;
import static com.chaeum.api.global.utils.MemberMissionConstants.DONATION_PROGRESS_MAX;
import static com.chaeum.api.global.utils.MemberMissionConstants.DONATION_PROGRESS_MIN;
import static com.chaeum.api.global.utils.MemberMissionConstants.FRIEND_PROGRESS_MAX;
import static com.chaeum.api.global.utils.MemberMissionConstants.FRIEND_PROGRESS_MIN;
import static com.chaeum.api.global.utils.MemberMissionConstants.ITEM_PROGRESS_MIN;
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
            case ITEM -> ITEM_PROGRESS_MIN;
            case FRIEND -> getRandomNumberInRange(FRIEND_PROGRESS_MIN, FRIEND_PROGRESS_MAX);
            case DONATION -> getRandomNumberInRange(DONATION_PROGRESS_MIN, DONATION_PROGRESS_MAX);
            case CAT -> getRandomNumberInRange(CAT_PROGRESS_MIN, CAT_PROGRESS_MAX);
        };
    }

    private int getRandomNumberInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
