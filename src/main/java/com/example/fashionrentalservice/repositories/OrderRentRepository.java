package com.example.fashionrentalservice.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;
import com.example.fashionrentalservice.model.dto.order.OrderRentDTO;

public interface OrderRentRepository extends JpaRepository<OrderRentDTO, Integer>{
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO>findAllOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO>findAllOrderRentByProductOwnerID(int productownerID);
	
	@Query("SELECT dto FROM OrderRentDTO dto WHERE dto.productownerDTO.productownerID = :productownerID AND dto.dateOrder >= :startDate")
	List<OrderRentDTO> findTotalOrderRent1MonthByProductOwnerID(@Param("productownerID") int productownerID, @Param("startDate") LocalDate startDate);
	
	@Query("SELECT dto FROM OrderRentDTO dto WHERE dto.productownerDTO.productownerID = :productownerID AND dto.dateOrder >= :startDate")
	List<OrderRentDTO> findTotalOrderRent1WeekByProductOwnerID(@Param("productownerID") int productownerID, @Param("startDate") LocalDate startDate);
	
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'PREPARE' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllPrepareOrderRentByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'COMPLETED' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllCompletedOrderRentByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'PREPARE' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllPrepareOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'COMPLETED' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllCompletedOrderRentByCustomerID(int customerID);
}
