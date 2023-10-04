package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.account.ProductOwnerDTO;

public interface ProductOwnerRepository extends JpaRepository<ProductOwnerDTO, Integer>{

}
