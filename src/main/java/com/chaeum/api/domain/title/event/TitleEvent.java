package com.chaeum.api.domain.title.event;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.title.entity.Title;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TitleEvent {

    private Long id;

    private Member receiver;

    private String content;

    public static TitleEvent from(Title title, Member receiver) {
        String message = "[" + title.getName().getDisplayName() + "] 칭호를 획득했습니다.";
        return TitleEvent.builder()
            .id(title.getId())
            .receiver(receiver)
            .content(message)
            .build();
    }
}
