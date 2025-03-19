package com.chaeum.api.global.auth.dto;

import com.chaeum.api.domain.member.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
// OAUTH2 인증 과정에 사용할 유저 객체 (비밀번호는 사용하지 않으므로 제외함)
public class OAuth2MemberDto {
    private final String name;
    private final String email;
    private final Role role;
}
