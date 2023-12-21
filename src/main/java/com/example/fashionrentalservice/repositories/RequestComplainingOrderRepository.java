package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.RequestComplainingOrderDTO;

public interface RequestComplainingOrderRepository extends JpaRepository<RequestComplainingOrderDTO, Integer>{
	@Query("select dto from RequestComplainingOrderDTO dto where dto.status = 'APPROVING' ORDER BY dto.requestComplainingOrderID DESC")
	List<RequestComplainingOrderDTO> findApprovingRequest();
	
	@Query("select dto from RequestComplainingOrderDTO dto where dto.orderRentDTO.orderRentID =?1 AND dto.status = 'NOT_APPROVED' ORDER BY dto.requestComplainingOrderID DESC")
	List<RequestComplainingOrderDTO> findRequestComplainingNotByOrderRentID(int orderRentID);
	

}
