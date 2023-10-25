package com.example.fashionrentalservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.fashionrentalservice.model.dto.product.StaffRequestedDTO;

public interface StaffRequestedRepository extends JpaRepository<StaffRequestedDTO, Integer>{
	@Query("select dto from StaffRequestedDTO dto where dto.staffDTO.staffID = ?1 AND dto.requestAddingProductDTO.status = 'NOT_APPROVED'")
	List<StaffRequestedDTO> findAllApprovedRequestedByStaffID(int staffID);

}
