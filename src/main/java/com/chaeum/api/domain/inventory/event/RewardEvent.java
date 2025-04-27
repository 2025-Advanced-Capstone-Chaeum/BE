package com.chaeum.api.domain.inventory.event;

import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RewardEvent {

    private Long id;

    private Member receiver;

    private String itemImageUrl;

    private String content;

    public static RewardEvent from(Item item, Member receiver) {
        String message = "[" + item.getName() + "] 를 획득했습니다. 인벤토리를 확인해보세요!";
        return RewardEvent.builder()
            .id(item.getId())
            .receiver(receiver)
            .itemImageUrl(item.getItemImageUrl())
            .content(message)
            .build();
    }
}
