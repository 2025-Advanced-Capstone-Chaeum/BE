package com.chaeum.api.domain.attendance.controller;

import com.chaeum.api.domain.attendance.service.AttendanceService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "출석 관리")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(
        summary = "출석 등록",
        description = """
            [모든 Role 가능]<br>
            현재 로그인한 회원과 오늘 날짜를 기준으로 출석을 등록합니다.
            """)
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("")
    public ApiResponse<Long> save(
    ) {
        Long id = attendanceService.save();
        return ApiResponse.success(id);
    }

    @Operation(
        summary = "출석 일자 조회",
        description = """
            [모든 Role 가능]<br>
            연도(year)와 월(month)이 파라미터로 전달되면, 해당 월에 로그인한 사용자의 출석 일자를 리스트로 반환합니다.
            """)
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("")
    public ApiResponse<List<Integer>> getTodayAttendance(
        @RequestParam int year,
        @RequestParam int month
    ) {
        List<Integer> attendanceDays = attendanceService.getAttendanceDaysByMonth(year, month);
        return ApiResponse.success(attendanceDays);
    }
}
