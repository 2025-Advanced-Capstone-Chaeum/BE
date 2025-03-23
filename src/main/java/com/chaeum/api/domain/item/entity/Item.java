package com.chaeum.api.domain.item.entity;

import com.chaeum.api.domain.item.dto.request.ItemRequestDto;
import com.chaeum.api.domain.item.dto.request.ItemUpdateRequestDto;
import com.chaeum.api.domain.item.dto.response.ItemResponseDto;
import com.chaeum.api.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "item")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "item_image_url")
    private String itemImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ItemCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private ItemGrade grade;

    public static Item toEntity(ItemResponseDto itemResponseDto) {
        return Item.builder()
            .name(itemResponseDto.getName())
            .itemImageUrl(itemResponseDto.getItemImageUrl())
            .category(itemResponseDto.getCategory())
            .grade(itemResponseDto.getGrade())
            .build();
    }

    public static Item toEntity(ItemRequestDto itemRequestDto) {
        return Item.builder()
            .name(itemRequestDto.getName())
            .itemImageUrl(null)
            .category(itemRequestDto.getCategory())
            .grade(itemRequestDto.getGrade())
            .build();
    }

    public void update(ItemUpdateRequestDto itemUpdateRequestDto) {
        Optional.ofNullable(itemUpdateRequestDto.getName()).ifPresent(this::setName);
        Optional.ofNullable(itemUpdateRequestDto.getCategory()).ifPresent(this::setCategory);
        Optional.ofNullable(itemUpdateRequestDto.getGrade()).ifPresent(this::setGrade);
    }
}
