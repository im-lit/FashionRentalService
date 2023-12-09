package com.example.fashionrentalservice.model.request;

import lombok.Data;

@Data
public class MessageRequest {
    int accountID;
    int roomID;
    String message;
}
