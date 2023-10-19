package com.example.fashionrentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashionrentalservice.model.dto.product.CategoryDTO;

public interface CategoryRepository extends JpaRepository<CategoryDTO, Integer>{

}
