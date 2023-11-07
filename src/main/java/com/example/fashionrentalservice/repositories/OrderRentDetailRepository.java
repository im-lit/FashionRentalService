package com.example.fashionrentalservice.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.order.OrderRentDetailDTO;

public interface OrderRentDetailRepository extends JpaRepository<OrderRentDetailDTO, Integer>{

	@Query("select dto from OrderRentDetailDTO dto where dto.productDTO.productID = ?1 AND ?2 BETWEEN dto.startDate AND dto.endDate")
	List<OrderRentDetailDTO> findAllOrderDetailByProductIDAndCheckDate(int productID, LocalDate date);
}
