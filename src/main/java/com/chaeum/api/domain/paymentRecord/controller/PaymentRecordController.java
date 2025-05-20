package com.chaeum.api.domain.paymentRecord.controller;

import com.chaeum.api.domain.paymentRecord.dto.request.PaymentCreateRequest;
import com.chaeum.api.domain.paymentRecord.dto.response.PaymentResponse;
import com.chaeum.api.domain.paymentRecord.entity.PaymentMethod;
import com.chaeum.api.domain.paymentRecord.entity.PaymentStatus;
import com.chaeum.api.domain.paymentRecord.service.PaymentRecordService;
import com.chaeum.api.global.response.ApiResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "결제 관리")
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    @Operation(summary = "결제 생성", description = "[DONOR or ADMIN만 가능] 결제 요청 정보를 저장합니다.")
    @PreAuthorize("hasAnyRole('DONOR', 'ADMIN')")
    @PostMapping("")
    public ApiResponse<Long> save(
        @Valid @RequestBody PaymentCreateRequest paymentCreateRequest
    ) throws IamportResponseException, IOException {
        Long id = paymentRecordService.save(paymentCreateRequest);
        return ApiResponse.success(id);
    }

    @Operation(summary = "결제 단건 조회", description = "[모든 Role 가능] 결제 ID로 결제 정보를 조회합니다.")
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> getPayment(
        @PathVariable(name = "paymentId") Long paymentId
    ) {
        PaymentResponse paymentResponse = paymentRecordService.getPayment(paymentId);
        return ApiResponse.success(paymentResponse);
    }

    @Operation(
        summary = "조건별 결제 조회",
        description = """
            [모든 Role 가능] 결제 수단, 상태, 시작날짜, 끝날짜를 기준으로 결제 내역을 조회합니다.<br>
            조건을 입력하지 않으면 전체 결제를 조회합니다. 날짜는 ISO 형식(예: 2024-03-01)으로 입력하세요.
            """
    )
    @PreAuthorize("hasRole('DONOR')")
    @GetMapping("/condition")
    public ApiResponse<List<PaymentResponse>> getPaymentsByCondition(
        @RequestParam(required = false) PaymentMethod method,
        @RequestParam(required = false) PaymentStatus status,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<PaymentResponse> payments = paymentRecordService.getPaymentsByCondition(method, status, from, to);
        return ApiResponse.success(payments);
    }

    @Operation(summary = "결제 삭제", description = "[DONOR or ADMIN만 가능] 결제 ID로 결제 정보를 삭제합니다.")
    @PreAuthorize("hasAnyRole('DONOR', 'ADMIN')")
    @DeleteMapping("/{paymentId}")
    public ApiResponse<Long> delete(
        @PathVariable(name = "paymentId") Long paymentId) {
        Long id = paymentRecordService.delete(paymentId);
        return ApiResponse.success(id);
    }
}
