package com.chaeum.api.global.auth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import static com.chaeum.api.global.auth.util.OAuth2ResponseUtil.getNestedMap;
import static com.chaeum.api.global.auth.util.OAuth2ResponseUtil.toStringOrNull;
import static com.chaeum.api.global.auth.util.OAuth2ResponseUtil.toStringOrDefault;

@RequiredArgsConstructor
public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> naverAccountMap;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = getNestedMap(naverAccountMap, "response");
        return toStringOrNull(response.get("email"));
    }

    @Override
    public String getName() {
        Map<String, Object> response = getNestedMap(naverAccountMap, "response");
        return toStringOrDefault(response.get("name"), "unknown");
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> response = getNestedMap(naverAccountMap, "response");
        return toStringOrNull(response.get("profile_image"));
    }
}
