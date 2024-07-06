package com.jeevanraksha.availability_service.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeevanraksha.availability_service.entity.DoctorAvailability;

public interface DoctorAvailabilityService {
  List<DoctorAvailability> getAllAvailabilities();
  Optional<DoctorAvailability> getAvailabilityById(UUID availabilityId);
  DoctorAvailability createAvailability(DoctorAvailability availability);
  Optional<DoctorAvailability> updateAvailability(UUID availabilityId, DoctorAvailability availability);
  void deleteAvailability(UUID availabilityId);
  List<DoctorAvailability> getAvailabilitiesByDoctorId(UUID doctorId);
}

