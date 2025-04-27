package com.chaeum.api.domain.friendship.event;

import com.chaeum.api.domain.friendship.entity.Friendship;
import com.chaeum.api.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FriendEvent {

    private Long id;

    private Member sender;

    private Member receiver;

    private String content;

    public static FriendEvent from(Friendship friendship) {
        String message = friendship.getSender() + "님이 친구 요청을 보냈습니다.";
        return FriendEvent.builder()
            .id(friendship.getId())
            .sender(friendship.getSender())
            .receiver(friendship.getReceiver())
            .content(message)
            .build();
    }
}
