package com.chaeum.api.domain.funding.repository;

import com.chaeum.api.domain.funding.entity.RecommendedFunding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendedFundingRepository extends JpaRepository<RecommendedFunding, Long> {

    void deleteByMemberId(Long memberId);
}
