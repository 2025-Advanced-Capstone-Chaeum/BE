package com.chaeum.api.domain.item.controller;

import com.chaeum.api.domain.item.dto.request.ItemRequestDto;
import com.chaeum.api.domain.item.dto.request.ItemUpdateRequestDto;
import com.chaeum.api.domain.item.dto.response.ItemResponseDto;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.service.ItemService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
@Tag(name = "Item", description = "아이템 관리")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "아이템 추가", description = "ADMIN 이상 추가 가능")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ApiResponse<Long> save(
        @Valid @RequestBody ItemRequestDto itemRequestDto
    ) {
        Long id = itemService.save(itemRequestDto);
        return ApiResponse.success(id);
    }

    @Operation(summary = "아이템 개별 조회", description = "모든 Role 조회 가능")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<ItemResponseDto> getItem(
        @RequestParam(name = "itemId") Long itemId
    ) {
        ItemResponseDto itemResponseDto = itemService.getItem(itemId);
        return ApiResponse.success(itemResponseDto);
    }

    @Operation(summary = "조건별 아이템 조회", description = "모든 Role 조회 가능</br>"
        + "조건을 하나라도 입력하지 않으면 전체 조회됨</br>")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/condition")
    public ApiResponse<List<ItemResponseDto>> getItemsByCondition(
        @RequestParam(name = "category", required = false) ItemCategory category,
        @RequestParam(name = "itemName", required = false) String itemName
    ) {
        List<ItemResponseDto> items = itemService.getItemsByCondition(category, itemName);
        return ApiResponse.success(items);
    }

    @Operation(summary = "아이템 변경", description = "ADMIN 이상 변경 가능")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("")
    public ApiResponse<Long> update(
        @RequestParam(name = "itemId") Long itemId,
        @Valid @RequestBody ItemUpdateRequestDto itemUpdateRequestDto
    ) {
        Long id = itemService.update(itemId, itemUpdateRequestDto);
        return ApiResponse.success(id);
    }

    @Operation(summary = "아이템 삭제", description = "ADMIN 이상 삭제 가능")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{itemId}")
    public ApiResponse<Long> delete(
        @PathVariable(name = "itemId") Long itemId
    ) {
        Long id = itemService.delete(itemId);
        return ApiResponse.success(id);
    }
}
