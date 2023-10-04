package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.account.CustomerDTO;

public interface CustomerRepository extends JpaRepository<CustomerDTO, Integer>{

}
