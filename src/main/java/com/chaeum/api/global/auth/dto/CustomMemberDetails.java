package com.chaeum.api.global.auth.dto;

import java.util.Collection;
import java.util.Collections;

import com.chaeum.api.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomMemberDetails implements UserDetails {

    // 스프링 시큐리티 전용 객체
    private final Member member;

    public CustomMemberDetails(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().getKey()));
    }

    @Override
    public String getPassword() {
        return ""; // 소셜로그인을 통해 인증 받으므로 비밀번호 불필요
    }

    @Override
    public String getUsername() {
        return member.getEmail(); // 이메일을 Security의 식별자로 사용
    }

    // 이하 계정 상태는 소셜 로그인이므로 따로 관리 안함
    @Override
    public boolean isAccountNonExpired() {
        return true;  // 계정 만료 없음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 계정 잠금 없음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 자격 증명 만료 없음
    }

    @Override
    public boolean isEnabled() {
        return true;  // 계정 활성화
    }
}
