package com.chaeum.api.domain.item.entity;

import com.chaeum.api.domain.item.dto.request.ItemCreateRequest;
import com.chaeum.api.domain.item.dto.request.ItemUpdateRequest;
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

    public static Item toEntity(ItemCreateRequest itemCreateRequest) {
        return Item.builder()
            .name(itemCreateRequest.getName())
            .itemImageUrl(null)
            .category(itemCreateRequest.getCategory())
            .grade(itemCreateRequest.getGrade())
            .build();
    }

    public void update(ItemUpdateRequest itemUpdateRequest) {
        Optional.ofNullable(itemUpdateRequest.getName()).ifPresent(this::setName);
        Optional.ofNullable(itemUpdateRequest.getCategory()).ifPresent(this::setCategory);
        Optional.ofNullable(itemUpdateRequest.getGrade()).ifPresent(this::setGrade);
    }
}
