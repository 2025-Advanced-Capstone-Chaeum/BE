package com.chaeum.api.domain.attendance.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceCreateRequest {

    @NotNull
    @Schema(description = "출석 ID", example = "1")
    private Long attendanceId;
}
