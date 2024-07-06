package com.jeevanraksha.appointment_service.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jeevanraksha.appointment_service.dto.DoctorAvailability;

@FeignClient(name = "AVAILABILITY-SERVICE", url="${availability.url}", configuration = FeignClientConfiguration.class)
public interface DoctorAvailabilityClient {
    @GetMapping("/availabilities/doctor/{doctorId}")
    List<DoctorAvailability> getAvailabilitiesByDoctorId(@PathVariable UUID doctorId);
}
