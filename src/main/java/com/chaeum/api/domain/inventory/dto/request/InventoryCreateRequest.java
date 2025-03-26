package com.chaeum.api.domain.inventory.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryCreateRequest {

    @NotNull
    @Schema(description = "아이템 id", example = "1")
    private Long itemId;
}
