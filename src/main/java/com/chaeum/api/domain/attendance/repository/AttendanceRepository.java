package com.chaeum.api.domain.attendance.repository;

import com.chaeum.api.domain.attendance.entity.Attendance;
import com.chaeum.api.domain.member.entity.Member;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByMemberAndDate(Member member, LocalDate date);

    List<Attendance> findAllByMemberAndDateBetween(Member member, LocalDate startDate, LocalDate endDate);
}
