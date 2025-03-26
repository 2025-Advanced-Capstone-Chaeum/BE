package com.chaeum.api.domain.inventory.dto.response;

import com.chaeum.api.domain.inventory.entity.Inventory;
import com.chaeum.api.global.pagination.provider.CreatedAtProvider;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponse implements CreatedAtProvider {

    private Long id;

    private Long itemId;

    private Boolean isWearing;

    private int quantity;

    private LocalDateTime createdAt;

    public static InventoryResponse toDto(Inventory inventory) {
        return InventoryResponse.builder()
            .id(inventory.getId())
            .itemId(inventory.getItem().getId())
            .isWearing(inventory.getIsWearing())
            .quantity(inventory.getQuantity())
            .createdAt(inventory.getCreatedAt())
            .build();
    }
}
