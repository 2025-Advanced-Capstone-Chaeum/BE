package com.chaeum.api.global.auth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import static com.chaeum.api.global.auth.util.OAuth2ResponseUtil.getNestedMap;
import static com.chaeum.api.global.auth.util.OAuth2ResponseUtil.toStringOrNull;
import static com.chaeum.api.global.auth.util.OAuth2ResponseUtil.toStringOrDefault;

@RequiredArgsConstructor
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> kakaoAccountMap;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        Map<String, Object> account = getNestedMap(kakaoAccountMap, "kakao_account");
        return toStringOrDefault(account.get("email"), "no_email");
    }

    @Override
    public String getName() {
        Map<String, Object> account = getNestedMap(kakaoAccountMap, "kakao_account");
        Map<String, Object> profile = getNestedMap(account, "profile");
        return toStringOrDefault(profile.get("nickname"), "unknown");
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> account = getNestedMap(kakaoAccountMap, "kakao_account");
        Map<String, Object> profile = getNestedMap(account, "profile");
        return toStringOrNull(profile.get("profile_image_url"));
    }
}
