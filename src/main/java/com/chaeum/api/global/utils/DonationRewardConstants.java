package com.chaeum.api.global.utils;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DonationRewardConstants {

    public static final int POINT_REWARD_MIN = 10;
    public static final int POINT_REWARD_MAX = 500;

    public static final int INTERACTION_REWARD_MIN = 1;
    public static final int INTERACTION_REWARD_MAX = 3;

    public static final int INTERACTION_REWARD_COUNT = 2;

    public static final List<String> INTERACTION_TYPES = List.of(
        "밥주기", "쓰다듬기", "놀아주기"
    );
}