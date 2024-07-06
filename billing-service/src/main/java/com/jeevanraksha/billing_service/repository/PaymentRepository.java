package com.jeevanraksha.billing_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeevanraksha.billing_service.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
  List<Payment> findByInvoiceId(UUID invoiceId);
}
