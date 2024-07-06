package com.jeevanraksha.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class HealthController {
    @GetMapping("/health")
    public String healthCheck() {
        return "Ok";
    }
}
