package com.jeevanraksha.patient_service.entity;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class InsuranceDetails {
    private String provider;
    private String policyNumber;
    private LocalDate validUntil;
}
