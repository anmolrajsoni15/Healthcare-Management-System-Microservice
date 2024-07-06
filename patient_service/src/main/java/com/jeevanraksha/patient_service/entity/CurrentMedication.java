package com.jeevanraksha.patient_service.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CurrentMedication {
    private String medication;
    private String dosage;
    private String frequency;
}
