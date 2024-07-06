package com.jeevanraksha.notification_service.service;

import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    public String getTemplate(String templateName, String payload) {
        // Here you can implement a template engine like FreeMarker, Thymeleaf, or any custom logic to generate the message content.
        // For simplicity, we are just returning the payload as the message content.
        return templateName + "\n" + payload;
    }
}
