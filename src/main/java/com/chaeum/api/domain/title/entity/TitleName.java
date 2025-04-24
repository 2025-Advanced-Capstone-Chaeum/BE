package com.chaeum.api.domain.title.entity;

import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import java.util.Arrays;
import java.util.Comparator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TitleName {

    SAESSAK("나눔의 씨앗", "첫 기부 시 획득", 1),
    SPROUT("희망의 새싹", "5회 기부 시 획득", 5),
    GROWING_TREE("성장하는 나무", "10회 기부 시 획득", 10),
    FRUIT_TREE("열매 맺는 나무", "20회 기부 시 획득", 20),
    FOREST_GUARDIAN("희망의 숲지기", "50회 기부 시 획득", 50),
    LEGENDARY_TREE("전설의 나무", "100회 기부 시 획득", 100);

    private final String displayName;
    private final String description;
    private final int minDonationCount;

    public static TitleName getMatchedByCount(long count) {
        return Arrays.stream(TitleName.values())
            .sorted(Comparator.comparingInt(TitleName::getMinDonationCount).reversed())
            .filter(title -> count >= title.getMinDonationCount())
            .findFirst()
            .orElseThrow(() -> ChaeumException.from(ErrorCode.NOT_ELIGIBLE_FOR_TITLE));
    }
}
