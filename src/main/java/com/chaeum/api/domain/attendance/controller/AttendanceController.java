package com.chaeum.api.domain.attendance.controller;

import com.chaeum.api.domain.attendance.dto.request.AttendanceCreateRequest;
import com.chaeum.api.domain.attendance.service.AttendanceService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "출석 관리")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(summary = "출석 등록", description = "[모든 Role 가능]")
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping("")
    public ApiResponse<Long> save(
        @Valid @RequestBody AttendanceCreateRequest attendanceCreateRequest
    ) {
        Long id = attendanceService.save(attendanceCreateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "출석 일자 조회", description = "[모든 Role 가능]")
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
