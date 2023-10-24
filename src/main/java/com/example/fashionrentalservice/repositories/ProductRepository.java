package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.ProductDTO;
import com.example.fashionrentalservice.model.dto.product.ProductImgDTO;

public interface ProductRepository extends JpaRepository<ProductDTO, Integer>{
	@Query("select dto from ProductDTO dto where dto.productownerDTO.productownerID = ?1")
	List<ProductDTO>findAllByProductOwnerID(int productownerID);
	
	@Query("select dto from ProductImgDTO dto where dto.productDTO.productID = ?1")
	List<ProductImgDTO>findAllByProductID(int productownerID);
}
