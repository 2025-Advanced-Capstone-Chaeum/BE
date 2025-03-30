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
        Long memberId = memberService.getCurrentLoginMemberId();
        Cat cat = findByMemberId(memberId);
        return CatInformationResponse.toDto(cat);
    }

    @Transactional
    public List<Integer> addExperience(BigInteger gainedExp) {
        Long memberId = memberService.getCurrentLoginMemberId();
        Cat cat = findByMemberId(memberId);
        return cat.addExpAndGetLevelUps(gainedExp);
    }

    public Cat findByMemberId(Long memberId) {
        return catRepository.findByMemberId(memberId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.CAT_NOT_FOUND));
    }
}
