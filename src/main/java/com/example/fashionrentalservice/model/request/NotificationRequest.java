package com.example.fashionrentalservice.model.request;

import lombok.Data;

@Data
public class NotificationRequest {
    int accountID;
    String title;
    String message;
}
