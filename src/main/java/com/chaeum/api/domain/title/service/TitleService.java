package com.chaeum.api.domain.title.service;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.title.dto.response.TitleResponse;
import com.chaeum.api.domain.title.entity.Title;
import com.chaeum.api.domain.title.entity.TitleName;
import com.chaeum.api.domain.title.repository.TitleRepository;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;
    private final LoginMemberProvider loginMemberProvider;

    @Transactional
    public void giveTitle(int donationCount) {
        Member member = loginMemberProvider.getCurrentLoginMemberWithTitles();
        TitleName title = getTitleToGive(donationCount);
        if (hasTitle(member, title)) return;
        titleRepository.save(Title.create(title, member));
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

    private TitleName getTitleToGive(int count) {
        if (count >= 100) return TitleName.LEGENDARY_TREE;
        if (count >= 50) return TitleName.FOREST_GUARDIAN;
        if (count >= 20) return TitleName.FRUIT_TREE;
        if (count >= 10) return TitleName.GROWING_TREE;
        if (count >= 5) return TitleName.SPROUT;
        if (count >= 1) return TitleName.SAESSAK;
        throw ChaeumException.from(ErrorCode.NOT_ELIGIBLE_FOR_TITLE);
    }

    private boolean hasTitle(Member member, TitleName title) {
        return member.getTitles().stream().anyMatch(t -> t.getName() == title);
    }
}
