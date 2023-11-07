package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO;

public interface RequestAddingProductRepository extends JpaRepository<RequestAddingProductDTO, Integer>{
	@Query("SELECT dto FROM RequestAddingProductDTO dto WHERE dto.status = 'APPROVING' AND dto.productDTO.status = 'WAITING' ORDER BY dto.requestAddingProductID DESC")
	List<RequestAddingProductDTO>findApprovingRequest();

}
