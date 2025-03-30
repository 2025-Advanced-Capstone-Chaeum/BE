package com.chaeum.api.domain.inventory.service;

import com.chaeum.api.domain.cat.service.CatService;
import com.chaeum.api.domain.inventory.dto.request.InventoryCreateRequest;
import com.chaeum.api.domain.inventory.dto.response.InventoryResponse;
import com.chaeum.api.domain.inventory.entity.Inventory;
import com.chaeum.api.domain.inventory.repository.InventoryRepository;
import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.service.ItemService;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.pagination.cursorResult.CreatedAtCursorResult;
import com.chaeum.api.global.utils.ExpConstants;
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
    private final CatService catService;

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
        Long memberId = memberService.getCurrentLoginMemberId();
        List<Inventory> inventories = findByMemberId(memberId);

        List<InventoryResponse> filteredInventories = inventories.stream()
            .filter(inventory -> inventory.getItem().getCategory() == category)
            .filter(inventory -> cursor == null || inventory.getCreatedAt().isAfter(cursor))
            .sorted(Comparator.comparing(Inventory::getCreatedAt))
            .map(InventoryResponse::toDto)
            .collect(Collectors.toList());

        return CreatedAtCursorResult.of(filteredInventories, cursor, limit);
    }

    @Transactional(readOnly = true)
    public List<Long> getWearingInventoryItems() {
        Long memberId = memberService.getCurrentLoginMemberId();
        List<Inventory> inventories = inventoryRepository.findByMemberIdAndIsWearing(
            memberId, true);
        return inventories.stream()
            .map(Inventory::getItem)
            .filter(item -> item.isCategoryIn(ItemCategory.DECORATION, ItemCategory.INTERIOR))
            .map(Item::getId)
            .toList();
    }

    @Transactional
    public Long delete(Long inventoryId) {
        Inventory inventory = findByInventoryId(inventoryId);
        if (inventory.getItem().getCategory() == ItemCategory.INTERACTION) {
            inventory.removeQuantity();
        } else {
            inventoryRepository.delete(inventory);
        }
        return inventory.getId();
    }

    @Transactional
    public void toggleInventory(Long inventoryId) {
        Inventory inventory = findByInventoryId(inventoryId);
        inventory.getItem().validateCategory(ItemCategory.DECORATION, ItemCategory.INTERIOR);
        inventory.toggleWearing();
    }

    @Transactional
    public void useInteractionItem(Long inventoryId) {
        Inventory inventory = findByInventoryId(inventoryId);
        inventory.getItem().validateCategory(ItemCategory.INTERACTION);
        inventory.removeQuantity();
        catService.addExperience(ExpConstants.INTERACTION);
    }

    public Inventory findByInventoryId(Long inventoryId) {
        return inventoryRepository.findById(inventoryId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.INVENTORY_NOT_FOUND));
    }

    public List<Inventory> findByMemberId(Long memberId) {
        return inventoryRepository.findByMemberId(memberId);
    }
}
