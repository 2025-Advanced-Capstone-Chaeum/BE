package com.chaeum.api.domain.payment.service;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.domain.payment.dto.request.PaymentCreateRequest;
import com.chaeum.api.domain.payment.dto.response.PaymentResponse;
import com.chaeum.api.domain.payment.entity.Payment;
import com.chaeum.api.domain.payment.entity.PaymentGatewayInfo;
import com.chaeum.api.domain.payment.entity.PaymentMethod;
import com.chaeum.api.domain.payment.entity.PaymentStatus;
import com.chaeum.api.domain.payment.repository.PaymentRepository;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siot.IamportRestClient.response.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberService memberService;
    private final IamportClient iamportClient;

    @Transactional
    public Long save(PaymentCreateRequest paymentCreateRequest) throws IamportResponseException, IOException {
        Payment payment = validateAndCreatePayment(paymentCreateRequest);
        paymentRepository.save(payment);
        return payment.getId();
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = findById(paymentId);
        return PaymentResponse.toDto(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByCondition(
            PaymentMethod method,
            PaymentStatus status,
            LocalDate from,
            LocalDate to
    ) {
        return paymentRepository.findAll().stream()
                .filter(payment -> isMethodMatch(payment, method))
                .filter(payment -> isStatusMatch(payment, status))
                .filter(payment -> isInDateRange(payment, from, to))
                .map(PaymentResponse::toDto)
                .toList();
    }

    @Transactional
    public Long delete(Long paymentId) {
        paymentRepository.deleteById(paymentId);
        return paymentId;
    }

    // 결제 생성 및 검즘
    private Payment validateAndCreatePayment(PaymentCreateRequest paymentCreateRequest)
            throws IamportResponseException, IOException {
        var iamportPayment = verifyIamportPayment(paymentCreateRequest);

        validateAmount(iamportPayment.getAmount(), paymentCreateRequest.getAmount());

        Member member = memberService.getCurrentLoginMember();
        PaymentGatewayInfo gatewayInfo = PaymentGatewayInfo.create(paymentCreateRequest.getPaymentGatewayInfoRequest());

        return Payment.create(member, paymentCreateRequest, gatewayInfo);
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
    private boolean isMethodMatch(Payment payment, PaymentMethod method) {
        return method == null || payment.getPaymentMethod() == method;
    }

    private boolean isStatusMatch(Payment payment, PaymentStatus status) {
        return status == null || payment.getStatus() == status;
    }

    private boolean isInDateRange(Payment payment, LocalDate from, LocalDate to) {
        LocalDateTime createdAt = payment.getCreatedAt();
        boolean afterFrom = from == null || !createdAt.isBefore(from.atStartOfDay());
        boolean beforeTo = to == null || !createdAt.isAfter(to.atStartOfDay());
        return afterFrom && beforeTo;
    }

    // Id로 조회
    private Payment findById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> ChaeumException.from(ErrorCode.PAYMENT_NOT_FOUND));
    }
}
