package com.example.fashionrentalservice.controller;

import com.example.fashionrentalservice.model.request.NotificationRequest;
import com.example.fashionrentalservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping("")
    public void testNotification(@RequestBody NotificationRequest notificationRequest) {
        notificationService.pushNotification(notificationRequest.getAccountID(), notificationRequest.getTitle(), notificationRequest.getMessage());
    }

    @GetMapping("{accountID}")
    public ResponseEntity getNotification(@PathVariable int accountID){
        return ResponseEntity.ok(notificationService.getNotification(accountID));
    }

    @PostMapping("{accountID}")
    public void readNotification(@PathVariable int accountID){
        notificationService.readNotification(accountID);
    }
}
