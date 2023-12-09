package com.example.fashionrentalservice.repositories;

import com.example.fashionrentalservice.model.dto.chat.MessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageDTO, Integer> {
}
