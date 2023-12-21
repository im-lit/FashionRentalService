package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.StaffRequestedDTO;

public interface StaffRequestedRepository extends JpaRepository<StaffRequestedDTO, Integer>{
	@Query("select dto from StaffRequestedDTO dto where dto.staffDTO.staffID = ?1 AND dto.requestAddingProductDTO.status = 'APPROVED' ORDER BY dto.staffRequestedID DESC")
	List<StaffRequestedDTO> findAllApprovedRequestedByStaffID(int staffID);
	
	@Query("select dto from StaffRequestedDTO dto where dto.staffDTO.staffID = ?1 AND dto.requestAddingProductDTO.status = 'NOT_APPROVED'ORDER BY dto.staffRequestedID DESC")
	List<StaffRequestedDTO> findAllNotApprovedRequestedByStaffID(int staffID);
	
	@Query("select dto from StaffRequestedDTO dto where dto.requestAddingProductDTO.requestAddingProductID = ?1 ")
	StaffRequestedDTO findRequestAddProduct(int requestAddID);
	
	@Query("select dto from StaffRequestedDTO dto where dto.requestComplainingOrderDTO.requestComplainingOrderID = ?1 ")
	StaffRequestedDTO findRequestComplaining(int requestComplainID);

}
