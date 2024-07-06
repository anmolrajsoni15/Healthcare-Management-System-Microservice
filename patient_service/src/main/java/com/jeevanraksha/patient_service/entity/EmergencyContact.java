package com.jeevanraksha.patient_service.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EmergencyContact {
    private String name;
    private String relationship;
    private String emContactNumber;
}