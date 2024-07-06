package com.jeevanraksha.appointment_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class DoctorAvailability {
  private UUID availabilityId;
    private UUID doctorId;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;
}
