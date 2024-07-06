package com.jeevanraksha.billing_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeevanraksha.billing_service.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
  List<Invoice> findByPatientId(UUID patientId);
}
