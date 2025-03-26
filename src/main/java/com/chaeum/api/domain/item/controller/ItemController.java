package com.chaeum.api.domain.item.controller;

import com.chaeum.api.domain.item.dto.request.ItemCreateRequest;
import com.chaeum.api.domain.item.dto.request.ItemUpdateRequest;
import com.chaeum.api.domain.item.dto.response.ItemResponse;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.service.ItemService;
import com.chaeum.api.global.pagination.cursorResult.IdCursorResult;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
        @Valid @RequestBody ItemCreateRequest itemCreateRequest
    ) {
        Long id = itemService.save(itemCreateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "아이템 개별 조회", description = "모든 Role 조회 가능")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<ItemResponse> getItem(
        @RequestParam(name = "itemId") Long itemId
    ) {
        ItemResponse itemResponse = itemService.getItem(itemId);
        return ApiResponse.success(itemResponse);
    }

    @Operation(summary = "조건별 아이템 조회", description = "모든 Role 조회 가능</br>"
        + "조건을 하나라도 입력하지 않으면 전체 조회됨</br>")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/condition")
    public ApiResponse<IdCursorResult<ItemResponse>> getItemsByCondition(
        @RequestParam(name = "category", required = false) ItemCategory category,
        @RequestParam(name = "itemName", required = false) String itemName,
        @RequestParam(name = "cursor", required = false) Long cursor,
        @RequestParam(name = "limit", defaultValue = "3") int limit
    ) {
        IdCursorResult<ItemResponse> items = itemService.getItemsByCondition(category, itemName, cursor, limit);
        return ApiResponse.success(items);
    }

    @Operation(summary = "아이템 변경", description = "ADMIN 이상 변경 가능")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("")
    public ApiResponse<Long> update(
        @RequestParam(name = "itemId") Long itemId,
        @Valid @RequestBody ItemUpdateRequest itemUpdateRequest
    ) {
        Long id = itemService.update(itemId, itemUpdateRequest);
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
