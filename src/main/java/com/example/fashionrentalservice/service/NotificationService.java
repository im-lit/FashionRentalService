package com.example.fashionrentalservice.service;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.notification.FCM;
import com.example.fashionrentalservice.model.dto.notification.Notification;
import com.example.fashionrentalservice.model.request.FcmNotification;
import com.example.fashionrentalservice.model.request.NotificationRegisterRequest;
import com.example.fashionrentalservice.model.request.NotificationRequest;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.FCMRepository;
import com.example.fashionrentalservice.repositories.NotificationRepository;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    FCMRepository fcmRepository;

    @Autowired
    FcmService fcmService;

    public void pushNotification(int accountID, String title, String message) {
        AccountDTO accountDTO = accountRepository.findAccountDtoByAccountID(accountID);
        Notification notification = new Notification();
        notification.setAccount(accountDTO);
        notification.setTitle(title);
        notification.setMessage(message);
        Notification newNotification = notificationRepository.save(notification);
        if (newNotification != null) {
            messagingTemplate.convertAndSend("/topic/notification/" + accountDTO.getAccountID(), title + "-" + message);
        }
        for (FCM fcm: accountDTO.getFcms()){
            FcmNotification fcmNotification = new FcmNotification();
            fcmNotification.setBody(message);
            fcmNotification.setTitle(title);
            fcmNotification.setToken(fcm.getToken());
            try{
                fcmService.sendPushNotification(fcmNotification);
            }catch (FirebaseMessagingException | FirebaseAuthException e){
                e.printStackTrace();
            }
        }
    }

    public List<Notification> getNotification(int accountID){
        AccountDTO accountDTO = accountRepository.findAccountDtoByAccountID(accountID);
        return accountDTO.getNotifications().stream().sorted(Comparator.comparing(Notification::getCreated).reversed()).collect(Collectors.toList());
    }

    public void readNotification(int accountID){
        AccountDTO accountDTO = accountRepository.findAccountDtoByAccountID(accountID);
        List<Notification> notifications = notificationRepository.findNotificationsByIsReadFalseAndAccount(accountDTO);

        for (Notification notification: notifications){
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
    }

    public FCM createFCM(NotificationRegisterRequest notificationRequest){
        AccountDTO accountDTO = accountRepository.findAccountDtoByAccountID(notificationRequest.getAccountID());
        FCM oldFCM = fcmRepository.findFCMByTokenAndAccount(notificationRequest.getFcm(), accountDTO);
        if(oldFCM != null){
            return oldFCM;
        }
        FCM fcm = new FCM();
        fcm.setAccount(accountDTO);
        fcm.setToken(notificationRequest.getFcm());
        return fcmRepository.save(fcm);
    }

}
