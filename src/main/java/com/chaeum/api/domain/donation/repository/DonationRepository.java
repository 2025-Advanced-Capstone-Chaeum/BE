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

    @Query("SELECT COUNT(d) FROM Donation d WHERE d.member.id = :memberId")
    int countByMemberId(@Param("memberId") Long memberId);

    @Query(value = "SELECT * FROM donation d " +
        "WHERE d.member_id = :memberId " +
        "AND EXTRACT(YEAR FROM d.created_at) = :year " +
        "AND EXTRACT(MONTH FROM d.created_at) = :month",
        nativeQuery = true)
    List<Donation> findByMemberIdAndYearAndMonth(@Param("memberId") Long memberId, @Param("year") int year,
        @Param("month") int month);

    @Query(value = "SELECT * FROM donation d " +
        "WHERE d.member_id = :memberId " +
        "AND EXTRACT(YEAR FROM d.created_at) = :year",
        nativeQuery = true)
    List<Donation> findByMemberIdAndYear(@Param("memberId") Long memberId, @Param("year") int year);

    @Query("""
            SELECT COUNT(DISTINCT d1.funding.id)
            FROM Donation d1
            JOIN Donation d2 ON d1.funding.id = d2.funding.id
            WHERE d1.member.id = :memberA
              AND d2.member.id = :memberB
        """)
    int countSameFundingDonations(@Param("memberA") Long memberA, @Param("memberB") Long memberB);
}
