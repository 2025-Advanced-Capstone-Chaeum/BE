package com.chaeum.api.global.health;

import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthz")
@Tag(name = "Server Health", description = "서버 상태를 확인하는 API")
public class HealthCheckController {

    @GetMapping
    @Operation(summary = "서버 헬스 체크 API", description = "서버가 정상 동작하는지 확인하는 API입니다.")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("OK"));
    }
}