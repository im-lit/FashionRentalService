package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.ProductImgDTO;

public interface ProductImgRepository extends JpaRepository<ProductImgDTO, Integer>{
	@Query("select dto from ProductImgDTO dto where dto.productDTO.productID = ?1")
	List<ProductImgDTO>findAllImgByProductID(int productownerID);
}
