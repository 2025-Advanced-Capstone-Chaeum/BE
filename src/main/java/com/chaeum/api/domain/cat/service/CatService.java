package com.chaeum.api.domain.cat.service;

import com.chaeum.api.domain.cat.dto.response.CatInformationResponse;
import com.chaeum.api.domain.cat.entity.Cat;
import com.chaeum.api.domain.cat.repository.CatRepository;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public CatInformationResponse getMyCatInformation() {
        Member member = memberService.getCurrentLoginMember();
        Cat cat = findByMemberId(member);
        return CatInformationResponse.toDto(cat);
    }

    @Transactional
    public List<Integer> addExperience(BigInteger gainedExp) {
        Member member = memberService.getCurrentLoginMember();
        Cat cat = findByMemberId(member);
        return cat.addExpAndGetLevelUps(gainedExp);
    }

    public Cat findByMemberId(Member member) {
        return catRepository.findByMemberId(member.getId())
            .orElseThrow(() -> ChaeumException.from(ErrorCode.CAT_NOT_FOUND));
    }
}
