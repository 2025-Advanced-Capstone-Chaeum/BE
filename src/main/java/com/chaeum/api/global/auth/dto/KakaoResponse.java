package com.chaeum.api.global.auth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        if (kakaoAccount != null && kakaoAccount.containsKey("email")) {
            return kakaoAccount.get("email").toString();
        }
        return "no_email";  // 이메일이 없는 경우 기본값 설정
    }

    @Override
    public String getName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        if (kakaoAccount != null) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            if (profile != null && profile.containsKey("nickname")) {
                return profile.get("nickname").toString();
            }
        }
        return "unknown";  // 기본값 설정
    }

    @Override
    public String getProfileImage(){
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        if (kakaoAccount != null) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            if (profile != null && profile.containsKey("profile_image_url")) {
                return profile.get("profile_image_url").toString();
            }
        }
        return null; // 프로필 이미지가 없을 경우 null 반환
    }
}
