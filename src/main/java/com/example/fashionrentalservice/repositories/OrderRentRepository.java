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
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'DELIVERY' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllDeliveryOrderRentByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'CONFIRMING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllConfirmingOrderRentByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'RENTING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllRentingOrderRentByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'RETURNING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllReturningOrderRentByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'REJECTING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllRejectingOrderRentByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'REJECTING_COMPLETED' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllRejectingCompletedOrderRentByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'CANCELED' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllCanceledOrderRentByProductOwnerID(int productownerID);
	
	@Query("select dto from OrderRentDTO dto where dto.productownerDTO.productownerID = ?1 AND dto.status = 'PENDING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllPendingOrderRentByProductOwnerID(int productownerID);
	
	
	
	
	
	
	
	
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'PREPARE' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllPrepareOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'COMPLETED' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllCompletedOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'DELIVERY' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllDeliveryOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'CONFIRMING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllConfirmingOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'RENTING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllRentingOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'RETURNING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllReturningOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'REJECTING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllRejectingOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'REJECTING_COMPLETED' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllRejectingCompletedOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'CANCELED' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllCanceledCompletedOrderRentByCustomerID(int customerID);
	
	@Query("select dto from OrderRentDTO dto where dto.customerDTO.customerID = ?1 AND dto.status = 'PENDING' ORDER BY dto.orderRentID DESC")
	List<OrderRentDTO> findAllPendingOrderRentByCustomerID(int customerID);
	
	
	
}
