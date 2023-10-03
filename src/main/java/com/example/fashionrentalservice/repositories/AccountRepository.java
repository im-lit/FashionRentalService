package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.AccountDTO;



public interface AccountRepository extends JpaRepository<AccountDTO, Integer>{

}
