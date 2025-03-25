package com.chaeum.api.domain.paymentRecord.service;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.domain.paymentRecord.dto.request.PaymentCreateRequest;
import com.chaeum.api.domain.paymentRecord.dto.response.PaymentResponse;
import com.chaeum.api.domain.paymentRecord.entity.PaymentRecord;
import com.chaeum.api.domain.paymentRecord.entity.PaymentGatewayInfo;
import com.chaeum.api.domain.paymentRecord.entity.PaymentMethod;
import com.chaeum.api.domain.paymentRecord.entity.PaymentStatus;
import com.chaeum.api.domain.paymentRecord.repository.PaymentRecordRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentRecordService {

    private final PaymentRecordRepository paymentRecordRepository;
    private final MemberService memberService;
    private final IamportClient iamportClient;

    @Transactional
    public Long save(PaymentCreateRequest paymentCreateRequest) throws IamportResponseException, IOException {
        PaymentRecord paymentRecord = validateAndCreatePayment(paymentCreateRequest);
        paymentRecordRepository.save(paymentRecord);
        return paymentRecord.getId();
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Long paymentId) {
        PaymentRecord paymentRecord = findById(paymentId);
        return PaymentResponse.toDto(paymentRecord);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByCondition(
            PaymentMethod method,
            PaymentStatus status,
            LocalDate from,
            LocalDate to
    ) {
        return paymentRecordRepository.findAll().stream()
                .filter(paymentRecord -> isMethodMatch(paymentRecord, method))
                .filter(paymentRecord -> isStatusMatch(paymentRecord, status))
                .filter(paymentRecord -> isInDateRange(paymentRecord, from, to))
                .map(PaymentResponse::toDto)
                .toList();
    }

    @Transactional
    public Long delete(Long paymentId) {
        paymentRecordRepository.deleteById(paymentId);
        return paymentId;
    }

    // 결제 생성 및 검즘
    private PaymentRecord validateAndCreatePayment(PaymentCreateRequest paymentCreateRequest)
            throws IamportResponseException, IOException {
        var iamportPayment = verifyIamportPayment(paymentCreateRequest);

        validateAmount(iamportPayment.getAmount(), paymentCreateRequest.getAmount());

        Member member = memberService.getCurrentLoginMember();
        PaymentGatewayInfo gatewayInfo = PaymentGatewayInfo.create(paymentCreateRequest.getPaymentGatewayInfoRequest());

        return PaymentRecord.create(member, paymentCreateRequest, gatewayInfo);
    }

    private com.siot.IamportRestClient.response.Payment verifyIamportPayment(PaymentCreateRequest request)
            throws IamportResponseException, IOException {
        var response = iamportClient.paymentByImpUid(request.getPaymentGatewayInfoRequest().getImportUid());
        if (response.getResponse() == null) {
            throw ChaeumException.from(ErrorCode.PAYMENT_VERIFY_FAILED);
        }
        return response.getResponse();
    }

    private void validateAmount(BigDecimal actual, BigDecimal expected) {
        if (actual == null || actual.compareTo(expected) != 0) {
            throw ChaeumException.from(ErrorCode.PAYMENT_AMOUNT_MISMATCH);
        }
    }


    // 조건별 결제 조회 검증
    private boolean isMethodMatch(PaymentRecord paymentRecord, PaymentMethod method) {
        return method == null || paymentRecord.getPaymentMethod() == method;
    }

    private boolean isStatusMatch(PaymentRecord paymentRecord, PaymentStatus status) {
        return status == null || paymentRecord.getStatus() == status;
    }

    private boolean isInDateRange(PaymentRecord paymentRecord, LocalDate from, LocalDate to) {
        LocalDateTime createdAt = paymentRecord.getCreatedAt();
        boolean afterFrom = from == null || !createdAt.isBefore(from.atStartOfDay());
        boolean beforeTo = to == null || !createdAt.isAfter(to.atStartOfDay());
        return afterFrom && beforeTo;
    }

    // Id로 조회
    private PaymentRecord findById(Long paymentId) {
        return paymentRecordRepository.findById(paymentId)
                .orElseThrow(() -> ChaeumException.from(ErrorCode.PAYMENT_NOT_FOUND));
    }
}
