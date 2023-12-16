package com.example.fashionrentalservice.repositories;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.notification.FCM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FCMRepository extends JpaRepository<FCM, Integer> {
    FCM findFCMByTokenAndAccount(String token, AccountDTO accountDTO);
}
