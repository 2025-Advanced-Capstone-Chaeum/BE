package com.chaeum.api.domain.inventory.controller;

import com.chaeum.api.domain.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "회원 인벤토리 관리")
public class InventoryController {

    private final InventoryService inventoryService;
}
