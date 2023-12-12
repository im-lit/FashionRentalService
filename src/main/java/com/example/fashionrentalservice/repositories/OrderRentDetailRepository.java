package com.example.fashionrentalservice.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDetailDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;

public interface OrderRentDetailRepository extends JpaRepository<OrderRentDetailDTO, Integer>{

	@Query("select dto from OrderRentDetailDTO dto where dto.productDTO.productID = ?1 AND ?2 BETWEEN dto.startDate AND dto.endDate")
	OrderRentDetailDTO findAllOrderDetailByProductIDAndCheckDate(int productID, LocalDate date);
	
	@Query("select dto from OrderRentDetailDTO dto where dto.orderRentDTO.orderRentID = ?1 ")
	List<OrderRentDetailDTO>findAllOrderDetailByOrderRentID(int orderRentID);
	
	@Query("select dto from OrderRentDetailDTO dto where dto.productDTO.productID = ?1 ")
	OrderRentDetailDTO findOrderDetailByProductID(int productID);
	
	@Query("select dto from OrderRentDetailDTO dto where dto.productDTO.productID = ?1 AND dto.orderRentDTO.orderRentID = ?2")
	OrderRentDetailDTO findOrderDetailByProductIDAndOrderRentID(int productID, int orderRentID);
	
	@Query("select dto from OrderRentDetailDTO dto where dto.productDTO.productID = ?1 AND dto.productDTO.status = 'RENTING' ")
	OrderRentDetailDTO findOrderDetailByProductIDAndProductStatusRenting(int productID);
	
}
