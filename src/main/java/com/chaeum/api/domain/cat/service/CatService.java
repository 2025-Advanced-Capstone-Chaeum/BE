package com.chaeum.api.domain.cat.service;

import com.chaeum.api.domain.cat.dto.response.CatInformationResponse;
import com.chaeum.api.domain.cat.entity.Cat;
import com.chaeum.api.domain.cat.repository.CatRepository;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.memberMission.service.MemberMissionService;
import com.chaeum.api.domain.mission.entity.MissionType;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;

import java.math.BigInteger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final LoginMemberProvider loginMemberProvider;
    private final MemberMissionService memberMissionService;

    @Transactional(readOnly = true)
    public CatInformationResponse getMyCatInformation() {
        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
        Cat cat = findByMemberId(memberId);
        return CatInformationResponse.toDto(cat);
    }

    @Transactional
    public void addExperience(BigInteger gainedExp) {
        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
        Cat cat = findByMemberId(memberId);
        memberMissionService.increaseProgressByType(MissionType.CAT_EXP);
        cat.addExpAndGetLevelUps(gainedExp);
    }

    @Transactional
    public void registerCatForMember(Member member) {
        if (catRepository.existsByMemberId(member.getId())) return;
        catRepository.save(Cat.create(member));
    }

    public Cat findByMemberId(Long memberId) {
        return catRepository.findByMemberId(memberId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.CAT_NOT_FOUND));
    }
}
