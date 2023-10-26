package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.ProductDTO;

public interface ProductRepository extends JpaRepository<ProductDTO, Integer>{
	@Query("select dto from ProductDTO dto where dto.productownerDTO.productownerID = ?1")
	List<ProductDTO>findAllByProductOwnerID(int productownerID);
	

	
    @Query("SELECT dto FROM ProductDTO dto WHERE dto.status NOT IN ('BLOCKED', 'WAITING')")
    List<ProductDTO> findAllActiveProducts();
}
