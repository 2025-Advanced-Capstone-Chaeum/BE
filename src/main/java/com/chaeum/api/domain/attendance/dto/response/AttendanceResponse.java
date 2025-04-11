package com.chaeum.api.domain.attendance.dto.response;

import com.chaeum.api.domain.attendance.entity.Attendance;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttendanceResponse {

    private Long id;
    private Long memberId;
    private LocalDate date;

    public static AttendanceResponse toDto(Attendance attendance) {
        return AttendanceResponse.builder()
            .id(attendance.getId())
            .memberId(attendance.getMember().getId())
            .date(attendance.getDate())
            .build();
    }
}
