package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.RequestAddingProductDTO;

public interface RequestAddingProductRepository extends JpaRepository<RequestAddingProductDTO, Integer>{
	@Query("select dto from RequestAddingProductDTO dto where dto.status = 'APPROVING' ")
	List<RequestAddingProductDTO>findApprovingRequest();

}
