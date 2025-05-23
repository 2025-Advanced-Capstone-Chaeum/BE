package com.chaeum.api.global.auth.dto;

import com.chaeum.api.domain.member.entity.Member;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public record OAuth2MemberDto(String email, String name, String profileImage,
                              Collection<? extends GrantedAuthority> authorities) {

    public static OAuth2MemberDto create(Member member) {
        return new OAuth2MemberDto(
            member.getEmail(),
            member.getName(),
            member.getProfileImage(),
            List.of(new SimpleGrantedAuthority(member.getRole().getKey()))
        );
    }
}
