package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.account.StaffDTO;

public interface StaffRepository extends JpaRepository<StaffDTO, Integer>{

}
