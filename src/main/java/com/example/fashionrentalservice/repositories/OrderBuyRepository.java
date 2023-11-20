package com.example.fashionrentalservice.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.fashionrentalservice.model.dto.order.OrderBuyDTO;

public interface OrderBuyRepository extends JpaRepository<OrderBuyDTO, Integer>{
	
	//----------------------------------------------------Customer----------------------------------------------------
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'PENDING' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllPendingOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'CANCELED' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllCanceledOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'PREPARE' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllPrepareOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'DELIVERY' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllDeliveryOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'CONFIRMING' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllConfirmingOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'REJECTING' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllRejectingOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'REJECTING_COMPLETED' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllRejectingCompletedOrderBuyByCustomerID(int customerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'COMPLETED' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllCompletedOrderBuyByCustomerID(int customerID);
	
	
	
	//----------------------------------------------------PO------------------------------------------------------------------------------
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllOrderBuyByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'PENDING' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllPendingOrderBuyByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'CANCELED' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllCanceledOrderBuyByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'PREPARE' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllPrepareOrderBuyByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'DELIVERY' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllDeliveryOrderBuyByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'CONFIRMING' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllConfirmingOrderBuyByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'REJECTING' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllRejectingOrderBuyByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'REJECTING_COMPLETED' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllRejectingCompletedOrderBuyByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'COMPLETED' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO>findAllCompletedOrderBuyByProductOwnerID(int productownerID);
	
	

	@Query("select dto from OrderBuyDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status IN ('DELIVERY', 'CONFIRMING', 'COMPLETED') ORDER BY CASE WHEN dto.status = 'DELIVERY' THEN 1 WHEN dto.status = 'CONFIRMING' THEN 2 WHEN dto.status = 'COMPLETED' THEN 3 END")
	List<OrderBuyDTO> findAll3StatusOrderBuyByProductOwnerID(int productownerID);
	
	
	
	@Query("select dto from OrderBuyDTO dto where dto.status = 'CANCELED' ORDER BY dto.orderBuyID DESC")
	List<OrderBuyDTO> findAllCanceledOrderBuy();
	
	
	@Query("SELECT dto FROM OrderBuyDTO dto WHERE dto.productownerDTO.productownerID = :productownerID AND dto.dateOrder >= :startDate")
	List<OrderBuyDTO> getTotal1MonthByProductOwnerID(@Param("productownerID") int productownerID, @Param("startDate") LocalDate startDate);
	
	@Query("SELECT dto FROM OrderBuyDTO dto WHERE dto.productownerDTO.productownerID = :productownerID AND dto.dateOrder >= :startDate")
	List<OrderBuyDTO> getTotal1WeekByProductOwnerID(@Param("productownerID") int productownerID, @Param("startDate") LocalDate startDate);

}
