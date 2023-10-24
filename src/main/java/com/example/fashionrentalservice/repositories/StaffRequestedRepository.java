package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.product.StaffRequestedDTO;

public interface StaffRequestedRepository extends JpaRepository<StaffRequestedDTO, Integer>{

}
