package com.chaeum.api.global.auth.dto;

public interface OAuth2Response {

    String getProvider(); // ex. naver, kakao, ...

    String getEmail();

    String getName();

    String getProfileImage();
}
