package com.chaeum.api.domain.cat.repository;

import com.chaeum.api.domain.cat.entity.Cat;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {

    Optional<Cat> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
