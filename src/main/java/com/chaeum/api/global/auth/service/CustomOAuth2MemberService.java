package com.chaeum.api.global.auth.service;

import com.chaeum.api.domain.cat.service.CatService;
import com.chaeum.api.domain.inventory.service.InventoryService;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.entity.Role;
import com.chaeum.api.domain.member.entity.SocialLoginType;
import com.chaeum.api.domain.member.repository.MemberRepository;
import com.chaeum.api.global.auth.domain.CustomOAuth2Member;
import com.chaeum.api.global.auth.dto.KakaoResponse;
import com.chaeum.api.global.auth.dto.NaverResponse;
import com.chaeum.api.global.auth.dto.OAuth2MemberDto;
import com.chaeum.api.global.auth.dto.OAuth2Response;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final CatService catService;
    private final InventoryService inventoryService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = switch (registrationId) {
            case "naver" -> new NaverResponse(oAuth2User.getAttributes());
            case "kakao" -> new KakaoResponse(oAuth2User.getAttributes());
            default -> throw ChaeumException.from(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
        };

        Member member = memberRepository.findByEmail(oAuth2Response.getEmail())
            .map(existingMember -> updateMember(existingMember, oAuth2Response.getName()))
            .orElseGet(() -> joinMember(oAuth2Response));

        OAuth2MemberDto oAuth2MemberDto = new OAuth2MemberDto(member.getEmail(), member.getName(),
            member.getProfileImage());
        return new CustomOAuth2Member(oAuth2MemberDto, memberRepository);
    }

    private Member joinMember(OAuth2Response oAuth2Response) {
        Member member = memberRepository.save(createMemberForm(oAuth2Response));
        initNewMember(member);
        return member;
    }

    private Member createMemberForm(OAuth2Response oAuth2Response) {
        return Member.builder()
            .email(oAuth2Response.getEmail())
            .name(oAuth2Response.getName())
            .role(Role.DONOR)
            .points(BigDecimal.ZERO)
            .isBeneficiary(false)
            .socialLoginType(SocialLoginType.from(oAuth2Response.getProvider()))
            .build();
    }

    private void initNewMember(Member member) {
        catService.registerCatForMember(member);
        inventoryService.registerDefaultInteractionItems(member);
    }

    private Member updateMember(Member member, String newName) {
        member.setName(newName);
        if (member.getRole() == null) {
            member.setRole(Role.DONOR);
        }
        return memberRepository.save(member);
    }
}
