package com.chaeum.api.global.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
// OAUTH2 인증 과정에 사용할 유저 객체
public class OAuth2MemberDto {
    private final String email;
    private final String name;
}
