package com.chaeum.api.domain.paymentRecord.repository;

import com.chaeum.api.domain.paymentRecord.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {

}
