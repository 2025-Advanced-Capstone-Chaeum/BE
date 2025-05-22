package com.chaeum.api.global.auth.dto;

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
        Map<String, Object> account = getMap(kakaoAccountMap, "kakao_account");
        return toStringOrDefault(account.get("email"), "no_email");
    }

    @Override
    public String getName() {
        Map<String, Object> account = getMap(kakaoAccountMap, "kakao_account");
        Map<String, Object> profile = getMap(account, "profile");
        return toStringOrDefault(profile.get("nickname"), "unknown");
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> account = getMap(kakaoAccountMap, "kakao_account");
        Map<String, Object> profile = getMap(account, "profile");
        return toStringOrNull(profile.get("profile_image_url"));
    }

    private Map<String, Object> getMap(Map<String, Object> source, String key) {
        Object value = source.get(key);
        return value instanceof Map ? (Map<String, Object>) value : Map.of();
    }

    private String toStringOrDefault(Object obj, String defaultValue) {
        return obj != null ? obj.toString() : defaultValue;
    }

    private String toStringOrNull(Object obj) {
        return obj != null ? obj.toString() : null;
    }
}
