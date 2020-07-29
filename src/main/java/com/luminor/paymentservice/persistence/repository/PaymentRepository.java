package com.luminor.paymentservice.persistence.repository;

import com.luminor.paymentservice.persistence.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
