package com.chaeum.api.domain.mission.service;

import com.chaeum.api.domain.mission.dto.request.MissionCreateRequest;
import com.chaeum.api.domain.mission.dto.request.MissionUpdateRequest;
import com.chaeum.api.domain.mission.dto.response.MissionResponse;
import com.chaeum.api.domain.mission.entity.Mission;
import com.chaeum.api.domain.mission.entity.MissionType;
import com.chaeum.api.domain.mission.repository.MissionRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.pagination.cursorResult.IdCursorResult;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    @Transactional
    public Long save(MissionCreateRequest missionCreateRequest) {
        Mission mission = Mission.toEntity(missionCreateRequest);
        missionRepository.save(mission);
        return mission.getId();
    }

    @Transactional(readOnly = true)
    public MissionResponse getMission(Long missionId) {
        Mission mission = findById(missionId);
        return MissionResponse.toDto(mission);
    }

    // 내 미션들만 볼 수 있는 서비스 로직 작성

    @Transactional(readOnly = true)
    public IdCursorResult<MissionResponse> getMissionsByCondition(String missionName, MissionType missionType,
        Long cursor, int limit) {
        List<Mission> missions = missionRepository.findAll();
        List<Mission> filteredMissions = missions.stream()
            .filter(mission -> (missionName == null || missionName.trim().isEmpty()) ||
                mission.getName().toLowerCase().contains(missionName.toLowerCase()))
            .filter(mission -> missionType == null || mission.getType() == missionType)
            .filter(mission -> cursor == null || mission.getId() > cursor)
            .sorted(Comparator.comparingLong(Mission::getId))
            .collect(Collectors.toList());

        List<MissionResponse> missionsByName = filteredMissions.stream()
            .map(MissionResponse::toDto)
            .collect(Collectors.toList());

        return IdCursorResult.of(missionsByName, cursor, limit);
    }

    @Transactional
    public Long update(Long missionId, MissionUpdateRequest missionUpdateRequest) {
        Mission mission = findById(missionId);
        mission.update(missionUpdateRequest);
        return mission.getId();
    }

    @Transactional
    public Long delete(Long missionId) {
        missionRepository.deleteById(missionId);
        return missionId;
    }

    private Mission findById(Long missionId) {
        return missionRepository.findById(missionId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.MISSION_NOT_FOUND));
    }
}
