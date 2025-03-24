package com.chaeum.api.domain.item.dto.request;

import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.entity.ItemGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateRequest {

    @NotNull
    @Schema(description = "아이템명", example = "안경")
    private String name;

    @NotNull
    @Schema(description = "카테고리", example = "DECORATION")
    private ItemCategory category;

    @NotNull
    @Schema(description = "등급", example = "BRONZE")
    private ItemGrade grade;
}
