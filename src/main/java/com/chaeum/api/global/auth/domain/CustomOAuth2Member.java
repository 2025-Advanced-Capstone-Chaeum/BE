package com.chaeum.api.global.auth.domain;

import com.chaeum.api.global.auth.dto.OAuth2MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2Member implements OAuth2User {

    private final OAuth2MemberDto oAuth2MemberDto;
    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2MemberDto.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2MemberDto.getName();
    }

    public String getEmail() {
        return oAuth2MemberDto.getEmail();
    }
}
