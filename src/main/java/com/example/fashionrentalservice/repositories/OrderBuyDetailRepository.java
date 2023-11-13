package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDetailDTO;

public interface OrderBuyDetailRepository extends JpaRepository<OrderBuyDetailDTO, Integer>{	
	@Query("select dto from OrderBuyDetailDTO dto where dto.orderBuyDTO.orderBuyID = ?1 ")
	List<OrderBuyDetailDTO>findAllOrderDetailByOrderBuyID(int orderBuyID);
	
	@Query("select dto from OrderBuyDetailDTO dto where dto.productDTO.productID = ?1 ")
	OrderBuyDetailDTO findOrderDetailByProductID(int productID);
	
}
