package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.product.ProductDTO;

public interface ProductRepository extends JpaRepository<ProductDTO, Integer>{

}
