package com.chaeum.api.global.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuth2MemberDto {

    private final String email;
    private final String name;
}
