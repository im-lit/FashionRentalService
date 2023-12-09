package com.example.fashionrentalservice.controller;

import com.example.fashionrentalservice.model.dto.account.AccountDTO;
import com.example.fashionrentalservice.model.dto.chat.RoomDTO;
import com.example.fashionrentalservice.model.request.MessageRequest;
import com.example.fashionrentalservice.model.request.RoomRequest;
import com.example.fashionrentalservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping()
    public ResponseEntity createNewChat(@RequestBody RoomRequest roomRequest) {
        RoomDTO roomDTO = chatService.createNewRoom(roomRequest);
        return ResponseEntity.ok(roomDTO);
    }

    @GetMapping("{accountID}")
    public ResponseEntity getChatByAccountID(@PathVariable int accountID){
        List<RoomDTO> rooms = chatService.getRoomsByAccountID(accountID);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/detail/{roomID}")
    public ResponseEntity getChatDetail(@PathVariable int roomID){
        return ResponseEntity.ok(chatService.getRoomDetail(roomID));
    }

    @PostMapping("/send/{roomID}")
    public ResponseEntity sendMessage(@PathVariable int roomID, @RequestBody MessageRequest messageRequest){
        return ResponseEntity.ok(chatService.sendMessage(messageRequest));
    }

    @PostMapping("/typing/{roomID}/{name}")
    public void typingMessage(@PathVariable int roomID, @PathVariable String name){
        chatService.setTyping(roomID, name);
    }

}
