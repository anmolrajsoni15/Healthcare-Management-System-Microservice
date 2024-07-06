package com.jeevanraksha.availability_service.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeevanraksha.availability_service.entity.DoctorAvailability;
import com.jeevanraksha.availability_service.services.DoctorAvailabilityService;

@RestController
@RequestMapping("/availabilities")
public class DoctorAvailabilityController {
    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public List<DoctorAvailability> getAllAvailabilities() {
        return doctorAvailabilityService.getAllAvailabilities();
    }

    @GetMapping("/{availabilityId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<DoctorAvailability> getAvailabilityById(@PathVariable UUID availabilityId) {
        return doctorAvailabilityService.getAvailabilityById(availabilityId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public DoctorAvailability createAvailability(@RequestBody DoctorAvailability availability) {
        return doctorAvailabilityService.createAvailability(availability);
    }

    @PutMapping("/{availabilityId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<DoctorAvailability> updateAvailability(@PathVariable UUID availabilityId, @RequestBody DoctorAvailability availability) {
        return doctorAvailabilityService.updateAvailability(availabilityId, availability)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{availabilityId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<Void> deleteAvailability(@PathVariable UUID availabilityId) {
        doctorAvailabilityService.deleteAvailability(availabilityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public List<DoctorAvailability> getAvailabilitiesByDoctorId(@PathVariable UUID doctorId) {
        return doctorAvailabilityService.getAvailabilitiesByDoctorId(doctorId);
    }
}
