package com.chaeum.api.domain.title.service;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberQueryService;
import com.chaeum.api.domain.title.dto.response.TitleResponse;
import com.chaeum.api.domain.title.entity.Title;
import com.chaeum.api.domain.title.entity.TitleName;
import com.chaeum.api.domain.title.repository.TitleRepository;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;
    private final LoginMemberProvider loginMemberProvider;
    private final MemberQueryService memberQueryService;

    @Transactional
    public void giveTitle(long donationCount) {
        Member member = memberQueryService.getCurrentMemberWithTitles();
        TitleName.getMatchedByCount(donationCount)
            .filter(title -> !hasTitle(member, title))
            .ifPresent(title -> saveTitle(member, title));
    }

    @Transactional(readOnly = true)
    public TitleResponse getTitle() {
        return getTitle(loginMemberProvider.getCurrentLoginMember());
    }

    @Transactional(readOnly = true)
    public TitleResponse getTitle(Member member) {
        return titleRepository.findTopByMemberOrderByCreatedAtDesc(member)
            .map(TitleResponse::toDto)
            .orElse(TitleResponse.empty());
    }

    private boolean hasTitle(Member member, TitleName title) {
        return member.getTitles().stream().anyMatch(t -> t.getName() == title);
    }

    private void saveTitle(Member member, TitleName title) {
        titleRepository.save(Title.create(title, member));
    }
}
