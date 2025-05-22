package com.chaeum.api.global.auth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> naverAccountMap;

    private Map<String, Object> response() {
        return (Map<String, Object>) naverAccountMap.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        Map<String, Object> res = response();
        return res != null ? toStringOrNull(res.get("email")) : null;
    }

    @Override
    public String getName() {
        Map<String, Object> res = response();
        return res != null ? toStringOrNull(res.get("name")) : "unknown";
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> res = response();
        return res != null ? toStringOrNull(res.get("profile_image")) : null;
    }

    private String toStringOrNull(Object obj) {
        return obj != null ? obj.toString() : null;
    }
}
