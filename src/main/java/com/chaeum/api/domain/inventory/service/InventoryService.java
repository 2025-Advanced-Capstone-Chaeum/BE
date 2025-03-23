package com.chaeum.api.domain.inventory.service;

import com.chaeum.api.domain.inventory.dto.response.InventoryResponseDto;
import com.chaeum.api.domain.inventory.entity.Inventory;
import com.chaeum.api.domain.inventory.repository.InventoryRepository;
import com.chaeum.api.domain.item.dto.response.ItemResponseDto;
import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.repository.ItemRepository;
import com.chaeum.api.domain.item.service.ItemService;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemService itemService;
    private final MemberService memberService;
    private final ItemRepository itemRepository;

    @Transactional
    public Long save(Long itemId) {
        ItemResponseDto itemResponseDto = itemService.getItem(itemId);
        Item item = Item.toEntity(itemResponseDto);
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
    public List<InventoryResponseDto> getInventories() {
        Member member = memberService.getCurrentLoginMember();
        List<Inventory> inventories = inventoryRepository.findByMemberId(member.getId());
        return inventories.stream()
            .map(InventoryResponseDto::toDto)
            .toList();
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
