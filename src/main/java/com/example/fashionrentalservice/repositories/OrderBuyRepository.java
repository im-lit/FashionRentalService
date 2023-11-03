package com.example.fashionrentalservice.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;

public interface OrderBuyRepository extends JpaRepository<OrderBuyDTO, Integer>{
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllOrderBuyByProductOwnerID(int productownerID);
	
	@Query("SELECT dto FROM OrderBuyDTO dto WHERE dto.productownerDTO.productownerID = :productownerID AND dto.dateOrder >= :startDate")
	List<OrderBuyDTO> getTotal1MonthByProductOwnerID(@Param("productownerID") int productownerID, @Param("startDate") LocalDate startDate);
	
	@Query("SELECT dto FROM OrderBuyDTO dto WHERE dto.productownerDTO.productownerID = :productownerID AND dto.dateOrder >= :startDate")
	List<OrderBuyDTO> getTotal1WeekByProductOwnerID(@Param("productownerID") int productownerID, @Param("startDate") LocalDate startDate);



		
	

}
