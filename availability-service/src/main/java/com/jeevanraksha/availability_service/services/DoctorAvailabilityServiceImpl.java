package com.jeevanraksha.availability_service.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeevanraksha.availability_service.entity.DoctorAvailability;
import com.jeevanraksha.availability_service.repository.DoctorAvailabilityRepository;

@Service
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Override
    public List<DoctorAvailability> getAllAvailabilities() {
        return doctorAvailabilityRepository.findAll();
    }

    @Override
    public Optional<DoctorAvailability> getAvailabilityById(UUID availabilityId) {
        return doctorAvailabilityRepository.findById(availabilityId);
    }

    @Override
    public DoctorAvailability createAvailability(DoctorAvailability availability) {
        return doctorAvailabilityRepository.save(availability);
    }

    @Override
    public Optional<DoctorAvailability> updateAvailability(UUID availabilityId, DoctorAvailability updatedAvailability) {
        return doctorAvailabilityRepository.findById(availabilityId)
            .map(existingAvailability -> {
                existingAvailability.setDoctorId(updatedAvailability.getDoctorId());
                existingAvailability.setAvailableFrom(updatedAvailability.getAvailableFrom());
                existingAvailability.setAvailableTo(updatedAvailability.getAvailableTo());
                return doctorAvailabilityRepository.save(existingAvailability);
            });
    }

    @Override
    public void deleteAvailability(UUID availabilityId) {
        doctorAvailabilityRepository.deleteById(availabilityId);
    }

    @Override
    public List<DoctorAvailability> getAvailabilitiesByDoctorId(UUID doctorId) {
        return doctorAvailabilityRepository.findByDoctorId(doctorId);
    }
}
