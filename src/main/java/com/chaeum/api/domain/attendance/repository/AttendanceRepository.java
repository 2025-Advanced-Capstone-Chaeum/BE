package com.chaeum.api.domain.attendance.repository;

import com.chaeum.api.domain.attendance.entity.Attendance;
import com.chaeum.api.domain.member.entity.Member;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByMemberAndDate(Member member, LocalDate date);

    @Query(
        value = """
            SELECT EXTRACT(DAY FROM a.attendance_date)::int
            FROM attendance a
            WHERE a.member_id = :memberId
              AND a.attendance_date BETWEEN :startDate AND :endDate
            ORDER BY a.attendance_date
            """,
        nativeQuery = true)
    List<Integer> findAttendanceDays(Long memberId, LocalDate startDate, LocalDate endDate);
}
