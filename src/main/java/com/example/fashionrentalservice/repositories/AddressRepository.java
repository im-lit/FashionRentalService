package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.AddressDTO;

public interface AddressRepository extends JpaRepository<AddressDTO, Integer>{

}
