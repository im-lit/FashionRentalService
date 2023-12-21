package com.example.fashionrentalservice.repositories;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.chat.RoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoomRepository extends JpaRepository<RoomDTO, Integer> {

    List<RoomDTO> findRoomsByAccountsIsContaining(AccountDTO accountDTO);
    RoomDTO findRoomByAccountsIsContainingAndAccountsIsContaining(AccountDTO accountDTO1, AccountDTO accountDTO2);

    RoomDTO findRoomByRoomID(int roomID);
}
