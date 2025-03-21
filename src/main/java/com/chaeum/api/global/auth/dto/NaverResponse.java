package com.chaeum.api.global.auth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");
        return response != null ? response.get("id").toString() : null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");
        return response != null ? response.get("email").toString() : null;
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");
        return response != null ? response.get("name").toString() : null;
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> response = (Map<String, Object>) attribute.get("response");
        return response != null ? response.get("profile_image").toString() : null;
    }
}
