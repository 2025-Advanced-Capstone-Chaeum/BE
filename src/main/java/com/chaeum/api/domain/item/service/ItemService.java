package com.chaeum.api.domain.item.service;

import com.chaeum.api.domain.item.dto.request.ItemRequestDto;
import com.chaeum.api.domain.item.dto.request.ItemUpdateRequestDto;
import com.chaeum.api.domain.item.dto.response.ItemResponseDto;
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
    public Long save(ItemRequestDto itemRequestDto) {
        Item item = Item.toEntity(itemRequestDto);
        itemRepository.save(item);
        return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemResponseDto getItem(Long itemId) {
        Item item = findById(itemId);
        return ItemResponseDto.toDto(item);
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDto> getItemsByCondition(ItemCategory category, String itemName) {
        List<Item> items = itemRepository.findAll();

        return items.stream()
            .filter(item -> {
                boolean matchesCategory = (category == null) || item.getCategory() == category;
                boolean matchesName = (itemName == null || itemName.trim().isEmpty()) ||
                    item.getName().toLowerCase().contains(itemName.toLowerCase());
                return matchesCategory && matchesName;
            })
            .map(ItemResponseDto::toDto)
            .toList();
    }

    @Transactional
    public Long update(Long itemId, ItemUpdateRequestDto itemUpdateRequestDto) {
        Item item = findById(itemId);
        item.update(itemUpdateRequestDto);
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
