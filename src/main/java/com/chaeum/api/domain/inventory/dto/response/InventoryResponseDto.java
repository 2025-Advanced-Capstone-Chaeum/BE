package com.chaeum.api.domain.inventory.dto.response;

import com.chaeum.api.domain.inventory.entity.Inventory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponseDto {

    private Long itemId;

    private Boolean isWearing;

    private int quantity;

    public static InventoryResponseDto toDto(Inventory inventory) {
        return InventoryResponseDto.builder()
            .itemId(inventory.getItem().getId())
            .isWearing(inventory.getIsWearing())
            .quantity(inventory.getQuantity())
            .build();
    }
}
