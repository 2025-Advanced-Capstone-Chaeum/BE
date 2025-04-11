package com.chaeum.api.domain.attendance.service;

import com.chaeum.api.domain.attendance.entity.Attendance;
import com.chaeum.api.domain.attendance.repository.AttendanceRepository;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final LoginMemberProvider loginMemberProvider;

    @Transactional
    public Long save() {
        Member member = loginMemberProvider.getCurrentLoginMember();
        LocalDate today = LocalDate.now();
        validateDate(today);
        validateDuplicateAttendance(member, today);
        Attendance attendance = Attendance.create(member, today);
        return attendanceRepository.save(attendance).getId();
    }

    @Transactional(readOnly = true)
    public List<Integer> getAttendanceDaysByMonth(int year, int month) {
        validateYearMonth(year, month);
        Long memberId = loginMemberProvider.getCurrentLoginMember().getId();
        YearMonth targetMonth = YearMonth.of(year, month);
        LocalDate startDate = targetMonth.atDay(1);
        LocalDate endDate = targetMonth.atEndOfMonth();
        return attendanceRepository.findAttendanceDays(memberId, startDate, endDate);
    }

    private void validateYearMonth(int year, int month) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        validateDate(firstDayOfMonth);
    }

    private void validateDate(LocalDate date) {
        if (date == null ||
            date.getDayOfMonth() >
                YearMonth.of(date.getYear(), date.getMonthValue()).lengthOfMonth()) {
            throw ChaeumException.from(ErrorCode.INVALID_DATE);
        }
    }

    private void validateDuplicateAttendance(Member member, LocalDate date) {
        if (attendanceRepository.existsByMemberAndDate(member, date)) {
            throw ChaeumException.from(ErrorCode.ALREADY_ATTENDED_TODAY);
        }
    }
}
