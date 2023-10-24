package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.product.ProductImgDTO;

public interface ProductImgRepository extends JpaRepository<ProductImgDTO, Integer>{

}
