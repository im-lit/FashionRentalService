package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;

public interface OrderBuyRepository extends JpaRepository<OrderBuyDTO, Integer>{
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1")
	List<OrderBuyDTO>findAllOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1")
	List<OrderBuyDTO>findAllOrderBuyByProductOwnerID(int productownerID);
		
	

}
