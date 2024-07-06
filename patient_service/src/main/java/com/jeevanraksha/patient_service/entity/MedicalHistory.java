package com.jeevanraksha.patient_service.entity;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class MedicalHistory {
    private String condition;
    private String treatment;
    private LocalDate date;
}
