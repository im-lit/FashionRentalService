package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;

public interface OrderBuyRepository extends JpaRepository<OrderBuyDTO, Integer>{
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllOrderBuyByProductOwnerID(int productownerID);
	
//	@Query("SELECT SUM(dto.total) FROM OrderBuyDTO dto WHERE dto.productownerDTO.productownerID = ?1 AND DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= dto.dateOrder")
//	Double getTotalRevenue1MonthByProductOwnerID(int productownerID);
		
	

}
