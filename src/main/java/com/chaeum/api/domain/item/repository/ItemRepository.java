package com.chaeum.api.domain.item.repository;

import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategory(ItemCategory category);
}
