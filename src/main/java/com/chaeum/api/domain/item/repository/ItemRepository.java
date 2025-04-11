package com.chaeum.api.domain.item.repository;

import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.entity.ItemGrade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategory(ItemCategory category);

    List<Item> findByCategoryInAndGrade(List<ItemCategory> category, ItemGrade grade);
}
