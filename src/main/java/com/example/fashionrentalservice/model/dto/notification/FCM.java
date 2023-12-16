package com.example.fashionrentalservice.model.dto.notification;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_fcm" )
public class FCM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    String token;

    @ManyToOne
    @JoinColumn(name = "account_id")
    AccountDTO account;
}
