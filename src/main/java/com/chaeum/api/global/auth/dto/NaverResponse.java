package com.chaeum.api.global.auth.dto;

import com.chaeum.api.global.auth.util.OAuth2ResponseUtil;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> naverAccountMap;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = OAuth2ResponseUtil.getNestedMap(naverAccountMap, "response");
        return OAuth2ResponseUtil.toStringOrNull(response.get("email"));
    }

    @Override
    public String getName() {
        Map<String, Object> response = OAuth2ResponseUtil.getNestedMap(naverAccountMap, "response");
        return OAuth2ResponseUtil.toStringOrDefault(response.get("name"), "unknown");
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> response = OAuth2ResponseUtil.getNestedMap(naverAccountMap, "response");
        return OAuth2ResponseUtil.toStringOrNull(response.get("profile_image"));
    }
}
