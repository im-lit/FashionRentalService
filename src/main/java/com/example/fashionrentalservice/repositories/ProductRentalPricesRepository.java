package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.ProductRentalPricesDTO;

public interface ProductRentalPricesRepository extends JpaRepository<ProductRentalPricesDTO, Integer>{
	
	@Query("select dto from ProductRentalPricesDTO dto where dto.productDTO.productID = ?1 ORDER BY dto.rentPrice ASC")
	List<ProductRentalPricesDTO> findAllByProductID(int productID);
}
