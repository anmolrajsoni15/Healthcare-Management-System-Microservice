package com.jeevanraksha.appointment_service.dto;


import lombok.Data;

@Data
public class NotificationMessage {
    private String type;
    private Object payload;
}
