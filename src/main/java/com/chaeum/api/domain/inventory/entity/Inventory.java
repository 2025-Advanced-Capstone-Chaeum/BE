package com.chaeum.api.domain.inventory.entity;

import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "inventory")
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "is_wearing", nullable = false)
    private boolean isWearing;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public static Inventory create(Item item, Member member) {
        return Inventory.builder()
            .member(member)
            .item(item)
            .isWearing(false)
            .quantity(0)
            .build();
    }

    public void addQuantity() {
        this.quantity += 1;
    }

    public void removeQuantity() {
        this.quantity -= 1;
    }

    public void wear() {
        this.isWearing = true;
    }

    public void unwear() {
        this.isWearing = false;
    }
}
