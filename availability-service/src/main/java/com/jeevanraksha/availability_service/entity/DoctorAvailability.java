package com.jeevanraksha.availability_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DoctorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID availabilityId;
    private UUID doctorId;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;
}