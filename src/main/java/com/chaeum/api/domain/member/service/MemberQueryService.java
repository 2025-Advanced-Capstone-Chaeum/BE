package com.chaeum.api.domain.member.service;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;
    private final LoginMemberProvider loginMemberProvider;

    @Transactional(readOnly = true)
    public Member getCurrentMemberWithTitles() {
        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
        return memberRepository.findByIdWithTitles(memberId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND));
    }
}