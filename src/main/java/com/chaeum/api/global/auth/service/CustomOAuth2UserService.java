package com.chaeum.api.global.auth.service;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.domain.CustomOAuth2Member;
import com.chaeum.api.global.auth.dto.KakaoResponse;
import com.chaeum.api.global.auth.dto.NaverResponse;
import com.chaeum.api.global.auth.dto.OAuth2MemberDto;
import com.chaeum.api.global.auth.dto.OAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    // OAuth2 제공자로부터 사용자 정보를 가져와 처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 1. OAuth2 제공자 판별 (naver, kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = switch (registrationId) {
            case "naver" -> new NaverResponse(oAuth2User.getAttributes());
            case "kakao" -> new KakaoResponse(oAuth2User.getAttributes());
            default -> throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 제공자: " + registrationId);
        };

        // 2. 기존 회원 조회 및 존재 여부 확인 (존재: 이름 갱신, 없음: 새로 생성)
        Member member = memberRepository.findByEmail(oAuth2Response.getEmail())
                .map(existingMember -> updateMemberName(existingMember, oAuth2Response.getName()))
                .orElseGet(() -> joinMember(oAuth2Response));

        // 3. OAuth2Member 인가된 정보 반환
        OAuth2MemberDto oAuth2MemberDto = new OAuth2MemberDto(member.getEmail(), member.getName());
        return new CustomOAuth2Member(oAuth2MemberDto);
    }

    // 기존 회원의 이름을 업데이트하고 저장
    private Member updateMemberName(Member member, String newName) {
        member.setName(newName);
        return memberRepository.save(member);
    }

    // 새로운 회원을 생성하고 저장
    private Member joinMember(OAuth2Response oAuth2Response) {
        Member newMember = Member.builder()
                .email(oAuth2Response.getEmail())
                .name(oAuth2Response.getName())
                .build();
        return memberRepository.save(newMember);
    }
}