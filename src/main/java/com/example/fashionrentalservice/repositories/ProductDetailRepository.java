package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.product.ProductDetailDTO;

public interface ProductDetailRepository extends JpaRepository<ProductDetailDTO, Integer>{


}
