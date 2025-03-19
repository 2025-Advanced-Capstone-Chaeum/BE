package com.chaeum.api.global.auth.domain;

import com.chaeum.api.global.auth.dto.OAuth2MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2Member implements OAuth2User {

    private final OAuth2MemberDto oAuth2MemberDto;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(oAuth2MemberDto.getRole().getKey()));
    }

    @Override
    public String getName() {
        return oAuth2MemberDto.getName();
    }

    public String getEmail() {
        return oAuth2MemberDto.getEmail();
    }
}
