package com.chaeum.api.domain.item.service;

import com.chaeum.api.domain.item.dto.request.ItemCreateRequest;
import com.chaeum.api.domain.item.dto.request.ItemUpdateRequest;
import com.chaeum.api.domain.item.dto.response.ItemResponse;
import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.entity.ItemGrade;
import com.chaeum.api.domain.item.repository.ItemRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.pagination.cursorResult.IdCursorResult;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long save(ItemCreateRequest itemCreateRequest) {
        Item item = Item.toEntity(itemCreateRequest);
        itemRepository.save(item);
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemResponse getItem(Long itemId) {
        Item item = findById(itemId);
        return ItemResponse.toDto(item);
    }

    @Transactional(readOnly = true)
    public IdCursorResult<ItemResponse> getItemsByCondition(ItemCategory category, String itemName, Long cursor,
        int limit) {
        List<Item> items = itemRepository.findAll();
        List<Item> filteredItems = items.stream()
            .filter(item -> (category == null || item.getCategory() == category) &&
                (itemName == null || itemName.trim().isEmpty() ||
                    item.getName().toLowerCase().contains(itemName.toLowerCase())))
            .filter(item -> cursor == null || item.getId() > cursor)
            .sorted(Comparator.comparingLong(Item::getId))
            .collect(Collectors.toList());

        List<ItemResponse> itemsByCondition = filteredItems.stream()
            .map(ItemResponse::toDto)
            .collect(Collectors.toList());

        return IdCursorResult.of(itemsByCondition, cursor, limit);
    }

    @Transactional
    public Long update(Long itemId, ItemUpdateRequest itemUpdateRequest) {
        Item item = findById(itemId);
        item.update(itemUpdateRequest);
        return item.getId();
    }

    @Transactional
    public Long delete(Long itemId) {
        itemRepository.deleteById(itemId);
        return itemId;
    }

    @Transactional(readOnly = true)
    public List<Item> findByCategory(ItemCategory category) {
        return itemRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Item> findByCategoryInAndGrade(List<ItemCategory> categories, ItemGrade grade) {
        return itemRepository.findByCategoryInAndGrade(categories, grade);
    }

    public Item findById(Long itemId) {
        return itemRepository.findById(itemId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.ITEM_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Item findInteractionItemByType(String interactionType) {
        List<Item> interactionItems = itemRepository.findByCategory(ItemCategory.INTERACTION);
        return interactionItems.stream()
            .filter(item -> item.getName().equalsIgnoreCase(interactionType))
            .findFirst()
            .orElseThrow(() -> ChaeumException.from(ErrorCode.ITEM_NOT_FOUND));
    }
}
