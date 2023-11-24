package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.fashionrentalservice.model.dto.product.FavoriteProductDTO;
import com.example.fashionrentalservice.model.dto.product.VoucherDTO;
@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProductDTO, Integer>{
	@Query("SELECT dto FROM FavoriteProductDTO dto WHERE dto.customerDTO.customerID = ?1 and dto.status='ACTIVE'")
	List<FavoriteProductDTO> findFavoriteByCusID(int customerID);
	
	@Query("SELECT dto FROM FavoriteProductDTO dto WHERE dto.customerDTO.customerID = ?1 AND dto.productDTO.productID =?2")
	FavoriteProductDTO findByCustomerAndProduct(int customerID, int productID);
}
