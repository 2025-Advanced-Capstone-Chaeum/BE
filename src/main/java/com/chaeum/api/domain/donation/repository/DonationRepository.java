package com.chaeum.api.domain.donation.repository;

import com.chaeum.api.domain.donation.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByMemberIdOrderByCreatedAtDesc(Long memberId);

    @Query(value = "SELECT * FROM donation d " +
            "WHERE d.member_id = :memberId " +
            "AND EXTRACT(YEAR FROM d.created_at) = :year " +
            "AND EXTRACT(MONTH FROM d.created_at) = :month",
            nativeQuery = true)
    List<Donation> findByMemberIdAndYearAndMonth(@Param("memberId") Long memberId, @Param("year") int year, @Param("month") int month);

    @Query(value = "SELECT * FROM donation d " +
            "WHERE d.member_id = :memberId " +
            "AND EXTRACT(YEAR FROM d.created_at) = :year",
            nativeQuery = true)
    List<Donation> findByMemberIdAndYear(@Param("memberId") Long memberId, @Param("year") int year);
}
