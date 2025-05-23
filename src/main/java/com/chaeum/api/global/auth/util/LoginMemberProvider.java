package com.chaeum.api.global.auth.util;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.auth.dto.CustomMemberDetails;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginMemberProvider {

    public Member getCurrentLoginMember() {
        Object principal = getAuthentication().getPrincipal();
        if (principal instanceof CustomMemberDetails customMemberDetails) {
            return customMemberDetails.member();
        }
        throw ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND);
    }

    public Long getCurrentLoginMemberId() {
        return getCurrentLoginMember().getId();
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw ChaeumException.from(ErrorCode.UNAUTHORIZED);
        }
        return authentication;
    }
}
