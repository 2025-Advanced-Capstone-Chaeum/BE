package com.chaeum.api.global.auth.service;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.domain.CustomOAuth2User;
import com.chaeum.api.global.auth.dto.KakaoResponse;
import com.chaeum.api.global.auth.dto.NaverResponse;
import com.chaeum.api.global.auth.dto.OAuth2Response;
import com.chaeum.api.global.auth.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User); // 테스트 출력

        // OAuth2 제공자 판별 (naver, kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = switch (registrationId) {
            case "naver" -> new NaverResponse(oAuth2User.getAttributes());
            case "kakao" -> new KakaoResponse(oAuth2User.getAttributes());
            default -> throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 제공자: " + registrationId);
        };

        // 기존 회원 조회
        Optional<Member> existingMember = memberRepository.findByEmail(oAuth2Response.getEmail());

        // 기존 회원이 없으면 새로 생성
        Member member = existingMember.orElseGet(() -> Member.builder()
                .email(oAuth2Response.getEmail())
                .name(oAuth2Response.getName())
                .role(Role.DONOR) // 초기 권한은 기부자
                .build());

        // 기존 회원이라면 이메일 및 이름 업데이트
        member.setEmail(oAuth2Response.getEmail());
        member.setName(oAuth2Response.getName());

        // 회원 저장
        memberRepository.save(member);

        // MemberDTO 변환
        MemberDTO memberDTO = new MemberDTO(
                member.getEmail(),
                member.getName(),
                member.getRole().name()
        );

        return new CustomOAuth2User(memberDTO);
    }
}