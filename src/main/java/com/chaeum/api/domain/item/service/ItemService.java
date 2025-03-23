package com.chaeum.api.domain.item.service;

import com.chaeum.api.domain.item.dto.request.ItemCreateRequest;
import com.chaeum.api.domain.item.dto.request.ItemUpdateRequest;
import com.chaeum.api.domain.item.dto.response.ItemResponse;
import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.repository.ItemRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import java.util.List;
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
    public List<ItemResponse> getItemsByCondition(ItemCategory category, String itemName) {
        List<Item> items = itemRepository.findAll();

        return items.stream()
            .filter(item -> {
                boolean matchesCategory = (category == null) || item.getCategory() == category;
                boolean matchesName = (itemName == null || itemName.trim().isEmpty()) ||
                    item.getName().toLowerCase().contains(itemName.toLowerCase());
                return matchesCategory && matchesName;
            })
            .map(ItemResponse::toDto)
            .toList();
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

    private Item findById(Long itemId) {
        return itemRepository.findById(itemId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.ITEM_NOT_FOUND));
    }
}
