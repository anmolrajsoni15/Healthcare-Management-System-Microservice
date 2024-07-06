package com.jeevanraksha.patient_service.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Allergy {
    private String allergy;
    private String reaction;
}
