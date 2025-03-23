package com.chaeum.api.domain.inventory.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponseDto {

    private Long itemId;

    private boolean isWearing;

    private int quantity;
}
