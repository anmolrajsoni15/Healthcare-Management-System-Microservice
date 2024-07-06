package com.jeevanraksha.billing_service.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class InsuranceClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID claimId;
    private UUID patientId;
    private UUID invoiceId;
    private String insuranceProvider;
    private LocalDate claimDate;
    private BigDecimal claimAmount;
    private String status; // PENDING, APPROVED, REJECTED

}
