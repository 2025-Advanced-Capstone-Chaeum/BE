package com.chaeum.api.global.auth.dto;

import com.chaeum.api.global.utils.CustomMapUtil;
import com.chaeum.api.global.utils.CustomStringUtil;
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
        Map<String, Object> account = CustomMapUtil.getNestedMap(kakaoAccountMap, "kakao_account");
        return CustomStringUtil.toStringOrDefault(account.get("email"), "no_email");
    }

    @Override
    public String getName() {
        Map<String, Object> account = CustomMapUtil.getNestedMap(kakaoAccountMap, "kakao_account");
        Map<String, Object> profile = CustomMapUtil.getNestedMap(account, "profile");
        return CustomStringUtil.toStringOrDefault(profile.get("nickname"), "unknown");
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> account = CustomMapUtil.getNestedMap(kakaoAccountMap, "kakao_account");
        Map<String, Object> profile = CustomMapUtil.getNestedMap(account, "profile");
        return CustomStringUtil.toStringOrNull(profile.get("profile_image_url"));
    }
}
