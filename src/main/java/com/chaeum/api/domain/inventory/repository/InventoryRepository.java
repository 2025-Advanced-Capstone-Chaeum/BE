package com.chaeum.api.domain.inventory.repository;

import com.chaeum.api.domain.inventory.entity.Inventory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByMemberId(Long memberId);

    Optional<Inventory> findByItemId(Long itemId);
}
