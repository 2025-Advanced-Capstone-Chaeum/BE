package com.chaeum.api.domain.inventory.controller;

import com.chaeum.api.domain.inventory.dto.request.InventoryCreateRequest;
import com.chaeum.api.domain.inventory.dto.response.InventoryResponse;
import com.chaeum.api.domain.inventory.service.InventoryService;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.global.pagination.cursorResult.CreatedAtCursorResult;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "인벤토리 관리")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "아이템 카테고리별 인벤토리 목록 조회", description = "모든 Role 조회 가능")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/category")
    public ApiResponse<CreatedAtCursorResult<InventoryResponse>> getInventoriesByCategory(
        @RequestParam(name = "category") ItemCategory category,
        @RequestParam(name = "cursor", required = false) LocalDateTime cursor,
        @RequestParam(name = "limit", defaultValue = "3") int limit
    ) {
        CreatedAtCursorResult<InventoryResponse> inventories =
            inventoryService.getInventoriesByCategory(category, cursor, limit);
        return ApiResponse.success(inventories);
    }

    @Operation(summary = "인벤토리 추가", description = "모든 Role 추가 가능")
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("")
    public ApiResponse<Long> saveInventory(
        @RequestBody InventoryCreateRequest inventoryCreateRequest
    ) {
        Long id = inventoryService.save(inventoryCreateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "인벤토리 삭제", description = "모든 Role 삭제 가능")
    @PreAuthorize("hasRole('DONOR')")
    @DeleteMapping("/{inventoryId}")
    public ApiResponse<Long> deleteInventory(
        @PathVariable(name = "inventoryId") Long inventoryId
    ) {
        Long id = inventoryService.delete(inventoryId);
        return ApiResponse.success(id);
    }

    @Operation(
        summary = "인벤토리 아이템 착용/해제",
        description = """
            [모든 Role 사용 가능]<br>
            아이템 카테고리가 INTERACTION이 아닌 경우에만 가능합니다.
            """
    )
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("/{inventoryId}/toggle")
    public ApiResponse<Void> toggleInventory(
        @PathVariable(name = "inventoryId") Long inventoryId
    ) {
        inventoryService.toggleInventory(inventoryId);
        return ApiResponse.success();
    }

    @Operation(
        summary = "고양이 상호작용 아이템 사용",
        description = """
            [모든 Role 사용 가능]<br>
            아이템 카테고리가 INTERACTION인 경우에만 가능합니다
            """
    )
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("/{inventoryId}/use")
    public ApiResponse<Void> useInteractionItem(
        @PathVariable(name = "inventoryId") Long inventoryId
    ) {
        inventoryService.useInteractionItem(inventoryId);
        return ApiResponse.success();
    }

    @Operation(
        summary = "착용중인 인벤토리 아이템 조회",
        description = """
            [모든 Role 사용 가능]<br>
            아이템 카테고리가 INTERACTION이 아닌 경우에만 가능합니다.
            """
    )
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/wearing")
    public ApiResponse<List<Long>> getWearingInventoryItems() {
        List<Long> items = inventoryService.getWearingInventoryItems();
        return ApiResponse.success(items);
    }
}
