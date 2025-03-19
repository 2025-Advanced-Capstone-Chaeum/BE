package com.chaeum.api.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialLoginType {

    NAVER("NAVER", "네이버 로그인"),
    KAKAO("KAKAO", "카카오 로그인");

    private final String key;          // 로그인 타입 키 (예: NAVER, KAKAO)
    private final String description;  // 설명 (예: 네이버 로그인, 카카오 로그인)
}
