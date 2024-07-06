package com.jeevanraksha.availability_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeevanraksha.availability_service.entity.DoctorAvailability;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, UUID> {
  List<DoctorAvailability> findByDoctorId(UUID doctorId);
  List<DoctorAvailability> findByDoctorIdAndAvailableFromBetween(UUID doctorId, LocalDateTime start, LocalDateTime end);
}

