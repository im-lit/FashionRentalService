package com.example.fashionrentalservice.model.request;

import lombok.Data;

@Data
public class NotificationRegisterRequest {
    int accountID;
    String fcm;
}
