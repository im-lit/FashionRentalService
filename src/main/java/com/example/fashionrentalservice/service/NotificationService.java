package com.example.fashionrentalservice.service;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.notification.Notification;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.NotificationRepository;
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

}
