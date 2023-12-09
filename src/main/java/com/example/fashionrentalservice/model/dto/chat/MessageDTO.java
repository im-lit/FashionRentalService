package com.example.fashionrentalservice.model.dto.chat;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tbl_message")
public class MessageDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private int messageID;

    String message;

    Date createAt = new Date();

    @ManyToOne
    @JoinColumn(name = "account_id")
    AccountDTO account;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "room_id")
    RoomDTO room;
}
