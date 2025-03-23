package com.chaeum.api.domain.inventory.controller;

import com.chaeum.api.domain.inventory.dto.response.InventoryResponseDto;
import com.chaeum.api.domain.inventory.service.InventoryService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "인벤토리 관리")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "인벤토리 목록 조회", description = "모든 Role 조회 가능")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<List<InventoryResponseDto>> getInventories() {
        List<InventoryResponseDto> inventories = inventoryService.getInventories();
        return ApiResponse.success(inventories);
    }

    @Operation(summary = "인벤토리 추가", description = "모든 Role 추가 가능")
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("")
    public ApiResponse<Long> saveInventory(
        @RequestParam(name = "itemId") Long itemId
    ) {
        Long id = inventoryService.save(itemId);
        return ApiResponse.success(id);
    }

    @Operation(summary = "인벤토리 삭제", description = "모든 Role 삭제 가능")
    @PreAuthorize("hasRole('DONOR')")
    @DeleteMapping("")
    public ApiResponse<Long> deleteInventory(
        @RequestParam(name = "itemId") Long itemId
    ) {
        Long id = inventoryService.delete(itemId);
        return ApiResponse.success(id);
    }
}
