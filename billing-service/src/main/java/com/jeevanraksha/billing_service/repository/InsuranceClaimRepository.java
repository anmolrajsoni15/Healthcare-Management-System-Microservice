package com.jeevanraksha.billing_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeevanraksha.billing_service.entity.InsuranceClaim;

public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, UUID> {
  List<InsuranceClaim> findByPatientId(UUID patientId);
}
