package com.chaeum.api.domain.friendship.dto.response;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.title.entity.TitleName;
import com.chaeum.api.global.pagination.provider.IdProvider;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FriendshipResponse implements IdProvider {

    private Long id;

    private Long friendId;

    private String friendName;

    private String friendProfileImage;

    private List<TitleName> friendTitleName;

    private int sharedDonationCount;

    public static FriendshipResponse toDto(
        Member friend,
        List<TitleName> activeTitleNames,
        int sharedDonationCount
    ) {
        return FriendshipResponse.builder()
            .friendId(friend.getId())
            .friendName(friend.getName())
            .friendProfileImage(friend.getProfileImage())
            .friendTitleName(activeTitleNames)
            .sharedDonationCount(sharedDonationCount)
            .build();
    }
}
