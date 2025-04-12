package com.chaeum.api.global.auth.util;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.dto.CustomMemberDetails;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginMemberProvider {

    private final MemberRepository memberRepository;

    public Member getCurrentLoginMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw ChaeumException.from(ErrorCode.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomMemberDetails customMemberDetails) {
            return customMemberDetails.getMember();
        }
        throw ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND);
    }

    public Long getCurrentLoginMemberId() {
        return getCurrentLoginMember().getId();
    }

    public Member getCurrentLoginMemberWithTitles() {
        return memberRepository.findByIdWithTitles(getCurrentLoginMemberId())
            .orElseThrow(() -> ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND));
    }
}
