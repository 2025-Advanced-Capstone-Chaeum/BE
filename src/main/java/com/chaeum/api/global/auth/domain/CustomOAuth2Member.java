package com.chaeum.api.global.auth.domain;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.dto.OAuth2MemberDto;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
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
    private final MemberRepository memberRepository;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Member member = memberRepository.findByEmail(oAuth2MemberDto.getEmail())
                .orElseThrow(() -> ChaeumException.from(ErrorCode.MEMBER_NOT_FOUND));
        return List.of(new SimpleGrantedAuthority(member.getRole().getKey()));
    }

    @Override
    public String getName() {
        return oAuth2MemberDto.getName();
    }

    public String getEmail() {
        return oAuth2MemberDto.getEmail();
    }
}
