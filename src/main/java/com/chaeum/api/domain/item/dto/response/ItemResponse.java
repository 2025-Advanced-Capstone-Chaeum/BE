package com.chaeum.api.domain.item.dto.response;

import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.entity.ItemGrade;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponse {

    private Long id;

    private String name;

    private String itemImageUrl;

    private ItemCategory category;

    private ItemGrade grade;

    public static ItemResponse toDto(Item item) {
        return ItemResponse.builder()
            .id(item.getId())
            .name(item.getName())
            .itemImageUrl(item.getItemImageUrl())
            .category(item.getCategory())
            .grade(item.getGrade())
            .build();
    }
}
