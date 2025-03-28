package com.chaeum.api.domain.funding.repository;

import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.funding.entity.FundingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FundingRepository extends JpaRepository<Funding, Long> {

    List<Funding> findByStatusAndEndDateBefore(FundingStatus status, LocalDateTime endDate);
}
