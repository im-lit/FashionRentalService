package com.example.fashionrentalservice.model.dto.notification;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_notification" )
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    Date created = new Date();


    String title;
    String message;
    boolean isRead = false;
    @ManyToOne
    @JoinColumn(name = "account_id")
    AccountDTO account;
}
