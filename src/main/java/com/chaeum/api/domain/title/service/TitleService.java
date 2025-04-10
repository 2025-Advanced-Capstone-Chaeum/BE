package com.chaeum.api.domain.title.service;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.title.entity.Title;
import com.chaeum.api.domain.title.entity.TitleName;
import com.chaeum.api.domain.title.repository.TitleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

    @Transactional(readOnly = true)
    public List<TitleName> getActiveTitleNames(Member member) {
        return titleRepository.findByIsActiveTrue().stream()
            .map(Title::getName)
            .toList();
    }
}
