package com.chaeum.api.global.auth.dto;

public interface OAuth2Response {

    String getProvider(); // 제공자 (ex. naver, kakao, ...)

    String getProviderId(); // 제공자에서 발급해주는 번호

    String getEmail();

    String getName();
}
