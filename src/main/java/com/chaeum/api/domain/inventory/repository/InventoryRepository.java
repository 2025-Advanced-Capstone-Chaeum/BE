package com.chaeum.api.domain.inventory.repository;

import com.chaeum.api.domain.inventory.entity.Inventory;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByMemberId(Long memberId);

    List<Inventory> findByMemberIdAndIsWearing(Long memberId, boolean isWearing);

    Optional<Inventory> findByItemId(Long itemId);

    Optional<Inventory> findByMemberIdAndItemId(Long memberId, Long itemId);

    boolean existsByMemberIdAndItemId(Long memberId, Long itemId);

    @Query("SELECT i.item.id FROM Inventory i WHERE i.member.id = :memberId")
    Set<Long> findItemIdsByMemberId(@Param("memberId") Long memberId);
}
