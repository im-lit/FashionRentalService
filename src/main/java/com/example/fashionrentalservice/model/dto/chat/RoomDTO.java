package com.example.fashionrentalservice.model.dto.chat;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_room")
public class RoomDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private int roomID;

    private String name;

    Date lastUpdated = new Date();

    String lastMessage;

    @ManyToMany
    private Set<AccountDTO> accounts;

    @OneToMany(mappedBy = "room")
    private List<MessageDTO> messages;
}
