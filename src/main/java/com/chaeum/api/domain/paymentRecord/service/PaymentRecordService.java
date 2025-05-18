package com.chaeum.api.domain.paymentRecord.service;

import com.chaeum.api.domain.donation.entity.Donation;
import com.chaeum.api.domain.donation.service.DonationService;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.domain.paymentRecord.dto.request.PaymentCreateRequest;
import com.chaeum.api.domain.paymentRecord.dto.response.PaymentResponse;
import com.chaeum.api.domain.paymentRecord.entity.PaymentMethod;
import com.chaeum.api.domain.paymentRecord.entity.PaymentRecord;
import com.chaeum.api.domain.paymentRecord.entity.PaymentStatus;
import com.chaeum.api.domain.paymentRecord.repository.PaymentRecordRepository;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;
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

    private final IamportClient iamportClient;
    private final MemberService memberService;
    private final DonationService donationService;
    private final LoginMemberProvider loginMemberProvider;
    private final PaymentRecordRepository paymentRecordRepository;

    @Transactional
    public Long save(PaymentCreateRequest request) throws IamportResponseException, IOException {
        Member member = loginMemberProvider.getCurrentLoginMember();
        Donation donation = donationService.findById(request.getDonationId());

        validateIamportPayment(request.getImpUid(), request.getAmount(), donation);
        memberService.deductPoints(member, request.getPoints());

        PaymentRecord payment = PaymentRecord.create(member, request);
        paymentRecordRepository.save(payment);

        payment.updateStatus(PaymentStatus.COMPLETED);
        donationService.completeDonation(donation);

        return payment.getId();
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Long paymentId) {
        return PaymentResponse.toDto(findById(paymentId));
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByCondition(
        PaymentMethod method,
        PaymentStatus status,
        LocalDate from,
        LocalDate to
    ) {
        return paymentRecordRepository.findAll().stream()
            .filter(p -> method == null || p.getPaymentMethod() == method)
            .filter(p -> status == null || p.getStatus() == status)
            .filter(p -> isInDateRange(p.getCreatedAt(), from, to))
            .map(PaymentResponse::toDto)
            .toList();
    }

    @Transactional
    public Long delete(Long paymentId) {
        paymentRecordRepository.deleteById(paymentId);
        return paymentId;
    }

    private void validateIamportPayment(String impUid, BigDecimal expectedAmount, Donation donation) {
        try {
            Payment payment = iamportClient.paymentByImpUid(impUid).getResponse();
            if (payment == null) {
                donationService.failDonation(donation);
                throw ChaeumException.from(ErrorCode.PAYMENT_VERIFY_FAILED);
            }
            if (isAmountMismatch(payment.getAmount(), expectedAmount)) {
                donationService.failDonation(donation);
                throw ChaeumException.from(ErrorCode.PAYMENT_AMOUNT_MISMATCH);
            }
        } catch (IamportResponseException | IOException e) {
            donationService.failDonation(donation);
            throw ChaeumException.from(ErrorCode.PAYMENT_VERIFY_FAILED);
        }
    }

    private boolean isAmountMismatch(BigDecimal actual, BigDecimal expected) {
        return actual == null || actual.compareTo(expected) != 0;
    }

    private boolean isInDateRange(LocalDateTime created, LocalDate from, LocalDate to) {
        return (from == null || !created.isBefore(from.atStartOfDay())) &&
            (to == null || !created.isAfter(to.plusDays(1).atStartOfDay()));
    }

    private PaymentRecord findById(Long id) {
        return paymentRecordRepository.findById(id)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.PAYMENT_NOT_FOUND));
    }
}
