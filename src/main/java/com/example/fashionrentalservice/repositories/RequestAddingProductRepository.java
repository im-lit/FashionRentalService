package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO;

public interface RequestAddingProductRepository extends JpaRepository<RequestAddingProductDTO, Integer>{

}
