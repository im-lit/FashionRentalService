package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;

public interface OrderRentRepository extends JpaRepository<OrderRentDTO, Integer>{

}
