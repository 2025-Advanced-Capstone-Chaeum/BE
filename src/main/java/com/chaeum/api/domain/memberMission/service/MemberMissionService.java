package com.chaeum.api.domain.memberMission.service;

import static com.chaeum.api.global.utils.MemberMissionConstants.MEMBER_MISSION_ASSIGN_COUNT;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.memberMission.dto.response.MemberMissionResponse;
import com.chaeum.api.domain.memberMission.entity.MemberMission;
import com.chaeum.api.domain.memberMission.repository.MemberMissionRepository;
import com.chaeum.api.domain.mission.entity.Mission;
import com.chaeum.api.domain.mission.entity.MissionType;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionService {

    private final MemberMissionRepository memberMissionRepository;
    private final LoginMemberProvider loginMemberProvider;
    private final MemberMissionRandomService memberMissionRandomService;

    @Transactional
    public List<MemberMissionResponse> getMemberMissions() {
        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
        List<MemberMission> missions = getOrGenerateMemberMissions(memberId);
        return missions.stream()
            .map(MemberMissionResponse::toDto)
            .toList();
    }

    @Transactional
    public void increaseProgressByType(MissionType missionType) {
        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
        List<MemberMission> missions = findMissionsByMemberId(memberId);
        missions.stream()
            .filter(mission -> mission.getMission().getType() == missionType)
            .forEach(MemberMission::increaseProgress);
    }

    @Transactional
    public void increaseProgressByType(MissionType missionType, BigInteger amount) {
        int intAmount = amount.intValue();
        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
        List<MemberMission> missions = findMissionsByMemberId(memberId);
        missions.stream()
            .filter(mission -> mission.getMission().getType() == missionType)
            .forEach(mission -> mission.increaseProgress(intAmount));
    }

    private List<MemberMission> getOrGenerateMemberMissions(Long memberId) {
        List<MemberMission> existingMissions = memberMissionRepository.findByMemberId(memberId);
        LocalDate today = LocalDate.now();
        boolean allToday = existingMissions.stream()
            .allMatch(m -> m.getUpdatedAt().toLocalDate().isEqual(today));

        // 오늘 받은 미션이 모두 존재하면 그대로 반환
        if (!existingMissions.isEmpty() && allToday) {
            return existingMissions.stream()
                .limit(MEMBER_MISSION_ASSIGN_COUNT)
                .toList();
        }

        // 이전 날짜 미션이 하나라도 있으면 전체 삭제 후 새로 생성
        memberMissionRepository.deleteAll(existingMissions);

        Member member = loginMemberProvider.getCurrentLoginMember();
        List<Mission> missions = memberMissionRandomService.getRandomMissions();
        List<MemberMission> newMissions = missions.stream()
            .map(mission -> MemberMission.create(
                member,
                mission,
                memberMissionRandomService.getRandomProgressCount(mission.getType())
            ))
            .toList();

        return memberMissionRepository.saveAll(newMissions);
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 초기화
    @Transactional
    public void resetAllMemberMissions() {
        memberMissionRepository.deleteAllInBatch();
    }

    public List<MemberMission> findMissionsByMemberId(Long memberId) {
        return memberMissionRepository.findByMemberId(memberId);
    }
}
