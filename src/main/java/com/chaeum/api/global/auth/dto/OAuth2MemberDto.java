package com.chaeum.api.global.auth.dto;

import com.chaeum.api.domain.member.entity.Member;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Builder
public class OAuth2MemberDto {

    private final String email;
    private final String name;
    private final String profileImage;
    private final Collection<? extends GrantedAuthority> authorities;

    public OAuth2MemberDto(
        String email,
        String name,
        String profileImage,
        Collection<? extends GrantedAuthority> authorities
    ) {
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.authorities = authorities;
    }

    public static OAuth2MemberDto create(Member member) {
        return OAuth2MemberDto.builder()
            .email(member.getEmail())
            .name(member.getName())
            .profileImage(member.getProfileImage())
            .authorities(List.of(new SimpleGrantedAuthority(member.getRole().getKey())))
            .build();
    }
}
