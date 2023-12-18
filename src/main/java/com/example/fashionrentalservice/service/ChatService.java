package com.example.fashionrentalservice.service;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.chat.MessageDTO;
import com.example.fashionrentalservice.model.dto.chat.RoomDTO;
import com.example.fashionrentalservice.model.dto.notification.FCM;
import com.example.fashionrentalservice.model.request.FcmNotification;
import com.example.fashionrentalservice.model.request.MessageRequest;
import com.example.fashionrentalservice.model.request.RoomRequest;
import com.example.fashionrentalservice.repositories.AccountRepository;
import com.example.fashionrentalservice.repositories.MessageRepository;
import com.example.fashionrentalservice.repositories.RoomRepository;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    FcmService fcmService;

    public RoomDTO createNewRoom(RoomRequest roomRequest) {
        Set<AccountDTO> accounts = new HashSet<>();
        RoomDTO room = new RoomDTO();
        room.setAccounts(accounts);
        room.setName(roomRequest.getName());

        for (Integer accountId : roomRequest.getMembers()) {
            try {
                AccountDTO account = accountRepository.findAccountDtoByAccountID(accountId);
                accounts.add(account);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return roomRepository.save(room);
    }

    public List<RoomDTO> getRoomsByAccountID(int accountID) {
        AccountDTO accountDTO = accountRepository.findAccountDtoByAccountID(accountID);
        List<RoomDTO> rooms = roomRepository.findRoomsByAccountsIsContaining(accountDTO);
        if (rooms != null) {
            return rooms.stream().sorted(Comparator.comparing(RoomDTO::getLastUpdated).reversed()).collect(Collectors.toList());
        }
        return null;
    }

    public RoomDTO getRoomDetail(int roomID) {
        RoomDTO roomDTO = roomRepository.findRoomByRoomID(roomID);
        if(roomDTO != null) roomDTO.setMessages(roomDTO.getMessages().stream().sorted(Comparator.comparing(MessageDTO::getCreateAt)).collect(Collectors.toList()));
        return roomDTO;
    }

    public MessageDTO sendMessage(MessageRequest messageRequest) {
        AccountDTO accountDTO = accountRepository.findAccountDtoByAccountID(messageRequest.getAccountID());
        RoomDTO roomDTO = roomRepository.findRoomByRoomID(messageRequest.getRoomID());
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setAccount(accountDTO);
        messageDTO.setRoom(roomDTO);
        messageDTO.setMessage(messageRequest.getMessage());
        roomDTO.setLastUpdated(new Date());
        roomDTO.setLastMessage(messageRequest.getMessage());
        roomRepository.save(roomDTO);
        for (AccountDTO account : roomDTO.getAccounts()) {
            if (account.getAccountID() != messageRequest.getAccountID()) {
                messagingTemplate.convertAndSend("/topic/chat/" + account.getAccountID(), "New message");
                for (FCM fcm: account.getFcms()){
                    FcmNotification fcmNotification = new FcmNotification();
                    fcmNotification.setBody(messageRequest.getMessage());
                    fcmNotification.setTitle(accountDTO.getEmail());
                    fcmNotification.setToken(fcm.getToken());
                    try{
                        fcmService.sendPushNotification(fcmNotification);
                    }catch (FirebaseMessagingException | FirebaseAuthException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return messageRepository.save(messageDTO);
    }

    public void setTyping(int roomID, String name) {
        RoomDTO roomDTO = roomRepository.findRoomByRoomID(roomID);
        for (AccountDTO account : roomDTO.getAccounts()) {
            messagingTemplate.convertAndSend("/topic/chat/" + account.getAccountID(), "Typing: " + name);
        }
    }
}
