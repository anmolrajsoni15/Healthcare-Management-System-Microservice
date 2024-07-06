package com.jeevanraksha.notification_service.dto;


import lombok.Data;

@Data
public class NotificationMessage {
    private String type;
    private Object payload;

    public NotificationMessage() {
    }

    public NotificationMessage(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
