package com.chaeum.api.domain.inventory.dto.response;

import com.chaeum.api.domain.inventory.entity.Inventory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponse {

    private Long itemId;

    private Boolean isWearing;

    private int quantity;

    public static InventoryResponse toDto(Inventory inventory) {
        return InventoryResponse.builder()
            .itemId(inventory.getItem().getId())
            .isWearing(inventory.getIsWearing())
            .quantity(inventory.getQuantity())
            .build();
    }
}
