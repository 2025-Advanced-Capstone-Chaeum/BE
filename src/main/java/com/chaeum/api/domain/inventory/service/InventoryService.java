package com.chaeum.api.domain.inventory.service;

import com.chaeum.api.domain.inventory.dto.request.InventoryCreateRequest;
import com.chaeum.api.domain.inventory.dto.response.InventoryResponse;
import com.chaeum.api.domain.inventory.entity.Inventory;
import com.chaeum.api.domain.inventory.repository.InventoryRepository;
import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.repository.ItemRepository;
import com.chaeum.api.domain.item.service.ItemService;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.pagination.cursorResult.CreatedAtCursorResult;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemService itemService;
    private final MemberService memberService;

    @Transactional
    public Long save(InventoryCreateRequest inventoryCreateRequest) {
        Long itemId = inventoryCreateRequest.getItemId();
        Item item = itemService.findById(itemId);
        Member member = memberService.getCurrentLoginMember();

        Inventory inventory = inventoryRepository.findByItemId(itemId)
            .map(existingInventory -> {
                existingInventory.addQuantity();
                return existingInventory;
            })
            .orElseGet(() -> {
                Inventory newInventory = Inventory.create(item, member);
                inventoryRepository.save(newInventory);
                return newInventory;
            });

        return inventory.getId();
    }

    @Transactional(readOnly = true)
    public CreatedAtCursorResult<InventoryResponse> getInventoriesByCategory(
        ItemCategory category, LocalDateTime cursor, int limit
    ) {
        Member member = memberService.getCurrentLoginMember();
        List<Inventory> inventories = inventoryRepository.findByMemberId(member.getId());

        List<InventoryResponse> filteredInventories = inventories.stream()
            .filter(inventory -> inventory.getItem().getCategory() == category)
            .filter(inventory -> cursor == null || inventory.getCreatedAt().isAfter(cursor))
            .sorted(Comparator.comparing(Inventory::getCreatedAt))
            .map(InventoryResponse::toDto)
            .collect(Collectors.toList());

        return CreatedAtCursorResult.of(filteredInventories, cursor, limit);
    }

    @Transactional
    public Long delete(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.INVENTORY_NOT_FOUND));

        inventory.removeQuantity();
        if (inventory.getQuantity() == 0) {
            inventoryRepository.delete(inventory);
        }
        return inventory.getId();
    }
}
