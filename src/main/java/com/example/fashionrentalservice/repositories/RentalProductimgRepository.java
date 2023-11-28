package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.order.RentalProductImgDTO;

public interface RentalProductimgRepository extends JpaRepository<RentalProductImgDTO, Integer>{
	
	@Query("select dto from RentalProductImgDTO dto where dto.accountDTO.accountID = ?1 AND  dto.orderRentDTO.orderRentID = ?2")
	List<RentalProductImgDTO> findAllRentalProductImgByAccountID(int accountID, int orderRentID);
	
	@Query("select dto from RentalProductImgDTO dto where dto.orderRentDTO.orderRentID = ?1")
	List<RentalProductImgDTO> findAllRentalProductImgByOrderRentID(int orderRentID);
	
}
