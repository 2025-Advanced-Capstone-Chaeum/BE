package com.chaeum.api.global.auth.dto;

import com.chaeum.api.global.auth.util.OAuth2ResponseUtil;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> kakaoAccountMap;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        Map<String, Object> account = OAuth2ResponseUtil.getNestedMap(kakaoAccountMap, "kakao_account");
        return OAuth2ResponseUtil.toStringOrDefault(account.get("email"), "no_email");
    }

    @Override
    public String getName() {
        Map<String, Object> account = OAuth2ResponseUtil.getNestedMap(kakaoAccountMap, "kakao_account");
        Map<String, Object> profile = OAuth2ResponseUtil.getNestedMap(account, "profile");
        return OAuth2ResponseUtil.toStringOrDefault(profile.get("nickname"), "unknown");
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> account = OAuth2ResponseUtil.getNestedMap(kakaoAccountMap, "kakao_account");
        Map<String, Object> profile = OAuth2ResponseUtil.getNestedMap(account, "profile");
        return OAuth2ResponseUtil.toStringOrNull(profile.get("profile_image_url"));
    }
}
