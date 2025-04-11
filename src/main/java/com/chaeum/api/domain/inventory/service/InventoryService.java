package com.chaeum.api.domain.inventory.service;

import com.chaeum.api.domain.cat.service.CatService;
import com.chaeum.api.domain.donation.dto.response.DonationInteractionReward;
import com.chaeum.api.domain.inventory.dto.request.InventoryCreateRequest;
import com.chaeum.api.domain.inventory.dto.response.InventoryResponse;
import com.chaeum.api.domain.inventory.entity.Inventory;
import com.chaeum.api.domain.inventory.repository.InventoryRepository;
import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.service.ItemService;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.pagination.cursorResult.CreatedAtCursorResult;
import com.chaeum.api.global.utils.ExpConstants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final LoginMemberProvider loginMemberProvider;
    private final ItemService itemService;
    private final MemberService memberService;
    private final CatService catService;

    private static final int DEFAULT_ITEM_QUANTITY = 1;
    private static final int DEFAULT_INTERACTION_ITEM_QUANTITY = 5;

    @Transactional
    public Long save(InventoryCreateRequest inventoryCreateRequest) {
        Long itemId = inventoryCreateRequest.getItemId();
        Item item = itemService.findById(itemId);
        Member member = loginMemberProvider.getCurrentLoginMember();
        Inventory inventory = getInventory(itemId, item, member);

        return inventory.getId();
    }

    @Transactional
    public void saveRewardInventory(
        int pointReward,
        List<DonationInteractionReward> interactionRewards,
        Long nonInteractionRewardItemId
    ) {
        Member member = loginMemberProvider.getCurrentLoginMember();
        memberService.addPoints(member, BigDecimal.valueOf(pointReward));
        saveInteractionItems(interactionRewards, member);
        saveNonInteractionItem(nonInteractionRewardItemId, member);
    }

    @Transactional(readOnly = true)
    public CreatedAtCursorResult<InventoryResponse> getInventoriesByCategory(
            ItemCategory category, LocalDateTime cursor, int limit
    ) {
        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
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
        Long memberId = loginMemberProvider.getCurrentLoginMemberId();
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

    @Transactional
    public void registerDefaultInteractionItems(Member member) {
        itemService.findByCategory(ItemCategory.INTERACTION).stream()
                .filter(item -> !inventoryRepository.existsByMemberIdAndItemId(member.getId(), item.getId()))
                .map(item -> Inventory.create(item, member, DEFAULT_INTERACTION_ITEM_QUANTITY))
                .forEach(inventoryRepository::save);
    }

    @Transactional(readOnly = true)
    public Set<Long> findItemIdsByMemberId(Long memberId) {
        return inventoryRepository.findItemIdsByMemberId(memberId);
    }

    private Inventory getInventory(Long itemId, Item item, Member member) {
        return inventoryRepository.findByItemId(itemId)
            .map(existingInventory -> {
                existingInventory.addQuantity();
                return existingInventory;
            })
            .orElseGet(() -> {
                Inventory newInventory = Inventory.create(item, member, DEFAULT_ITEM_QUANTITY);
                inventoryRepository.save(newInventory);
                return newInventory;
            });
    }

    private void saveNonInteractionItem(Long nonInteractionRewardItemId, Member member) {
        if (nonInteractionRewardItemId != null) {
            boolean exists = inventoryRepository.existsByMemberIdAndItemId(member.getId(), nonInteractionRewardItemId);
            if (!exists) {
                Item nonInteractionItem = itemService.findById(nonInteractionRewardItemId);
                Inventory newInventory = Inventory.create(nonInteractionItem, member, DEFAULT_ITEM_QUANTITY);
                inventoryRepository.save(newInventory);
            }
        }
    }

    private void saveInteractionItems(List<DonationInteractionReward> interactionRewards, Member member) {
        interactionRewards.forEach(interactionReward -> {
            Item interactionItem = itemService.findInteractionItemByType(interactionReward.getInteractionType());
            Inventory inventory = inventoryRepository.findByMemberIdAndItemId(member.getId(), interactionItem.getId())
                .orElseThrow(() -> ChaeumException.from(ErrorCode.INVENTORY_NOT_FOUND));

            inventory.addQuantity(interactionReward.getQuantity());
        });
    }

    public Inventory findByInventoryId(Long inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> ChaeumException.from(ErrorCode.INVENTORY_NOT_FOUND));
    }

    public List<Inventory> findByMemberId(Long memberId) {
        return inventoryRepository.findByMemberId(memberId);
    }

}
